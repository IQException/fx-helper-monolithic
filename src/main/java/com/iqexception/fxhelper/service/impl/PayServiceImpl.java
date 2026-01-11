package com.iqexception.fxhelper.service.impl;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.pay.*;
import com.iqexception.fxhelper.dal.ext.FxAccountExtDao;
import com.iqexception.fxhelper.dal.ext.FxPayInfoExtDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxAccount;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxPayInfo;
import com.iqexception.fxhelper.service.PayService;
import com.iqexception.fxhelper.service.builder.PayInfoBuilder;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.support.TLVarManager;
import com.iqexception.fxhelper.util.BizException;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.wx.WxApi;
import com.iqexception.fxhelper.wx.WxPayApi;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import org.apache.commons.lang.StringUtils;
import org.jooq.DSLContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayServiceImpl extends BaseService implements PayService {

    private final FxPayInfoExtDao payInfoExtDao;

    private final FxAccountExtDao accountExtDao;

    private final WxPayApi wxPayApi;

    public PayServiceImpl(MessageSource messageSource,
                          WxApi wxApi,
                          DSLContext jooqClient,
                          JsonMapper jsonMapper,
                          WxPayApi wxPayApi,
                          FxPayInfoExtDao payInfoExtDao,
                          FxAccountExtDao accountExtDao) {
        super(messageSource, wxApi, jooqClient, jsonMapper);
        this.wxPayApi = wxPayApi;
        this.accountExtDao = accountExtDao;
        this.payInfoExtDao = payInfoExtDao;
    }

    @Override
    public Response<DepositResult> deposit(Request<DepositParam> request) {

        //https://pay.weixin.qq.com/wiki/doc/apiv3/assets/img/pay/wechatpay/6_2.png

        Long userId = TLVarManager.getCurrentUser().getUserId();
        String openId = TLVarManager.getCurrentUser().getOpenId();

        FxAccount account = accountExtDao.fetchOneByUserId(userId);
        //生成订单
        FxPayInfo payInfo = PayInfoBuilder.build(request.getParam().getAmount(),
                openId, account.getAccountId());
        // FIXME 状态管理
        payInfoExtDao.insert(payInfo);
        //请求微信支付，获得预付单标识
        //生成带签名支付信息
        PrepayWithRequestPaymentResponse response =
                wxPayApi.prepay("充值",
                        payInfo.getId().toString(),
                        LocalDateTime.now().plusMinutes(30),
                        request.getParam().getAmount(), openId);
        //返回支付信息
        DepositResult result = new DepositResult();
        result.setAppId(response.getAppId());
        result.setPaySign(response.getPaySign());
        result.setNonceStr(response.getNonceStr());
        result.setTimeStamp(response.getTimeStamp());
        result.setPackageVal(response.getPackageVal());
        result.setSignType(response.getSignType());

        return new Response<>(result);
        //用户调用wx.requestPayment发起支付
        //支付成功后回调接口
        //调用查单接口查询支付结果
    }

    @Override
    public Response<WithdrawResult> withdraw(Request<WithdrawParam> request) {

        Long userId = TLVarManager.getCurrentUser().getUserId();
        String openId = TLVarManager.getCurrentUser().getOpenId();

        FxAccount account = accountExtDao.fetchOneByUserId(userId);

        acct2wx(account.getAccountId(), openId, request.getParam().getAmount());
        return new Response<>();
    }

    @Override
    public FxPayInfo acct2wx(Long accountId, String openId, BigDecimal amount) {

        //生成订单
        FxPayInfo payInfo = PayInfoBuilder.build(amount, accountId, openId);
        // FIXME 状态管理
        payInfoExtDao.insert(payInfo);
        // FIXME SAGA事务
        //先扣余额
        int ret = accountExtDao.incrBalanceByAcctId(accountId, amount.negate());
        if (ret == 0)
            throw new BizException(ErrorCode.ACCOUNT_BALANCE_INSUFFICIENT);
        //转账
        wxPayApi.initTransfer(payInfo.getId(), amount, openId);

        return payInfo;
    }


    @Override
    public ResponseEntity<String> payNotify(String body,
                                            String sign,
                                            String serial,
                                            String nonce,
                                            String timestamp,
                                            String signType) {
        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder()
                .serialNumber(serial)
                .nonce(nonce)
                .signature(sign)
                .timestamp(timestamp)
                .body(body)
                .build();

        try {
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            Transaction trx = wxPayApi.parse(requestParam, Transaction.class);
            // 更新转出交易状态
            FxPayInfo payInfo = payInfoExtDao.fetchOneById(Long.valueOf(trx.getOutTradeNo()));
            if (StringUtils.isNotBlank(payInfo.getFromTrxId())) {
                //防止重复处理
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            payInfo.setFromTrxId(trx.getTransactionId());
            payInfo.setStatus(trx.getTradeState().name());
            payInfoExtDao.update(payInfo);
            // 创建转入交易（增加账户余额）
            // FIXME 异步处理
            if (trx.getTradeState() == Transaction.TradeStateEnum.SUCCESS) {
                accountExtDao.incrBalanceByAcctId(
                        Long.valueOf(trx.getOutTradeNo()),
                        new BigDecimal(trx.getAmount().getTotal() / 100));

            }
        } catch (ValidationException e) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            LOG.error("sign verification failed", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            // 如果处理失败，应返回 4xx/5xx 的状态码，例如 500 INTERNAL_SERVER_ERROR
            LOG.error("pay notify failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // 处理成功，返回 200 OK 状态码
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
