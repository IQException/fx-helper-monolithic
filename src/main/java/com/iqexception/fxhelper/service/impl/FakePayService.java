package com.iqexception.fxhelper.service.impl;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.pay.DepositParam;
import com.iqexception.fxhelper.controller.dto.pay.DepositResult;
import com.iqexception.fxhelper.controller.dto.pay.WithdrawParam;
import com.iqexception.fxhelper.controller.dto.pay.WithdrawResult;
import com.iqexception.fxhelper.dal.ext.FxAccountExtDao;
import com.iqexception.fxhelper.dal.ext.FxPayInfoExtDao;
import com.iqexception.fxhelper.dal.ext.FxShopExtDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxAccount;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxPayInfo;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxShop;
import com.iqexception.fxhelper.message.wxmsg.BillType;
import com.iqexception.fxhelper.message.wxmsg.WxMsgService;
import com.iqexception.fxhelper.service.PayService;
import com.iqexception.fxhelper.service.builder.PayInfoBuilder;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.support.PayChannel;
import com.iqexception.fxhelper.support.TLVarManager;
import com.iqexception.fxhelper.util.BizException;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.wx.WxApi;
import org.jooq.DSLContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FakePayService extends BaseService implements PayService {

    private final FxPayInfoExtDao payInfoExtDao;

    private final FxAccountExtDao accountExtDao;

    private final WxMsgService wxMsgService;

    private final FxShopExtDao shopExtDao;

    public FakePayService(MessageSource messageSource,
                          WxApi wxApi,
                          DSLContext jooqClient,
                          JsonMapper jsonMapper,
                          FxPayInfoExtDao payInfoExtDao,
                          FxAccountExtDao accountExtDao,
                          FxShopExtDao shopExtDao,
                          WxMsgService wxMsgService) {
        super(messageSource, wxApi, jooqClient, jsonMapper);
        this.accountExtDao = accountExtDao;
        this.payInfoExtDao = payInfoExtDao;
        this.shopExtDao = shopExtDao;
        this.wxMsgService = wxMsgService;
    }

    @Override
    public Response<DepositResult> deposit(Request<DepositParam> request) {

        Long userId = TLVarManager.getCurrentUser().getUserId();
        String openId = TLVarManager.getCurrentUser().getOpenId();

        FxAccount account = accountExtDao.fetchOneByUserId(userId);
        //生成订单
        FxPayInfo payInfo = new FxPayInfo();
        payInfo.setAmount(request.getParam().getAmount());
        payInfo.setFromAccount(openId);
        payInfo.setFromChannel(PayChannel.WX.getVal());
        payInfo.setToAccount(account.getAccountId().toString());
        payInfo.setToChannel(PayChannel.ACCOUNT.getVal());
        payInfo.setBizId(openId + BIZ_ID_SEPARATOR_WX + System.currentTimeMillis());
        //fake
        payInfo.setFromTrxId("fake");
        payInfo.setStatus("fake");
        // FIXME 状态管理
        payInfoExtDao.insert(payInfo);

        accountExtDao.incrBalanceByAcctId(account.getAccountId(), request.getParam().getAmount());

        List<FxShop> shops = shopExtDao.fetchByOwnerUserId(userId);
        wxMsgService.sendBillMessage(BillType.DEPOSIT, shops.get(0).getShopId(), shops.get(0).getShopName(),
                openId, request.getParam().getAmount(), payInfo.getId(), LocalDateTime.now());

        return new Response<>();
    }

    @Override
    public Response<WithdrawResult> withdraw(Request<WithdrawParam> request) {

        Long userId = TLVarManager.getCurrentUser().getUserId();
        String openId = TLVarManager.getCurrentUser().getOpenId();

        FxAccount account = accountExtDao.fetchOneByUserId(userId);

        FxPayInfo payInfo = acct2wx(account.getAccountId(), openId, request.getParam().getAmount());

        // FIXME 状态管理
        List<FxShop> shops = shopExtDao.fetchByOwnerUserId(userId);
        wxMsgService.sendBillMessage(
                BillType.WITHDRAW, shops.get(0).getShopId(), shops.get(0).getShopName(),
                openId, request.getParam().getAmount(),
                payInfo.getId(), LocalDateTime.now());

        return new Response<>();
    }

    @Override
    public FxPayInfo acct2wx(Long accountId, String openId, BigDecimal amount) {

        //生成订单
        FxPayInfo payInfo = PayInfoBuilder.build(amount, accountId, openId);
        payInfoExtDao.insert(payInfo);

        //先扣余额
        int ret = accountExtDao.incrBalanceByAcctId(accountId, amount.negate());
        if (ret == 0)
            throw new BizException(ErrorCode.ACCOUNT_BALANCE_INSUFFICIENT);
        // FIXME 转账
        // wxApi.initTransfer(payInfo.getId(), amount, openId);
        // FIXME 状态管理

        return payInfo;
    }

    @Override
    public ResponseEntity<String> payNotify(String body,
                                            String sign,
                                            String serial,
                                            String nonce,
                                            String timestamp,
                                            String signType) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
