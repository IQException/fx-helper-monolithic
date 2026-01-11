package com.iqexception.fxhelper.wx;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.http.DefaultHttpClientBuilder;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.transferbatch.TransferBatchService;
import com.wechat.pay.java.service.transferbatch.model.InitiateBatchTransferRequest;
import com.wechat.pay.java.service.transferbatch.model.TransferDetailInput;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.iqexception.fxhelper.wx.WxConstants.*;
import static com.iqexception.fxhelper.wx.WxConstants.APIv3_KEY;

public class WxPayApi {

    private final String PAYMENT_NOTIFY_URL = "fake-url";

    private final String TRANSFER_NOTIFY_URL = "fake-url";

    private final JsapiServiceExtension payService;

    private final NotificationParser parser;

    private final TransferBatchService transferService;

    private final WxConfig wxConfig;


    public WxPayApi(WxConfig wxConfig) {
        this.wxConfig = wxConfig;

        DefaultHttpClientBuilder httpClientBuilder = new DefaultHttpClientBuilder()
                .connectTimeoutMs(2000)
                .readTimeoutMs(2000)
                .writeTimeoutMs(2000)
                .disableRetryOnConnectionFailure();

        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(MCH_ID)
                .privateKeyFromPath(PRIVATE_KEY_PATH)
                .merchantSerialNumber(MCH_SERIAL_NO)
                .apiV3Key(APIv3_KEY)
                .httpClientBuilder(httpClientBuilder)
                .build();
        payService = new JsapiServiceExtension.Builder().config(config).build();
        parser = new NotificationParser((NotificationConfig) config);
        transferService = new TransferBatchService.Builder().config(config).build();
    }

    public PrepayWithRequestPaymentResponse prepay(
            String desc,
            String orderNo,
            LocalDateTime expireTime,
            BigDecimal total,
            String openId

    ) {

        PrepayRequest request = new PrepayRequest();
        request.setAppid(wxConfig.getAppId());
        request.setMchid(MCH_ID);
        request.setDescription(desc);
        request.setOutTradeNo(orderNo);
        request.setTimeExpire(expireTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        request.setNotifyUrl(PAYMENT_NOTIFY_URL);

        request.setAmount(new Amount());
        request.getAmount().setTotal(total.multiply(new BigDecimal(100)).intValue());

        request.setPayer(new Payer());
        request.getPayer().setOpenid(openId);

        return payService.prepayWithRequestPayment(request);

    }

    public <T> T parse(RequestParam requestParam, Class<T> decryptObjectClass) {

        return parser.parse(requestParam, decryptObjectClass);

    }

    public void initTransfer(Long orderNo, BigDecimal total, String openId) {

        long amount = total.multiply(new BigDecimal(100)).longValue();

        InitiateBatchTransferRequest request = new InitiateBatchTransferRequest();

        request.setAppid(wxConfig.getAppId());
        request.setNotifyUrl(TRANSFER_NOTIFY_URL);
        request.setBatchName("test");
        request.setBatchRemark("test");
        request.setTotalAmount(amount);
        request.setTotalNum(1);
        request.setOutBatchNo(orderNo.toString());

        TransferDetailInput transferDetail = new TransferDetailInput();
        transferDetail.setOutDetailNo(orderNo.toString());
        transferDetail.setTransferAmount(amount);
        transferDetail.setOpenid(openId);
        transferDetail.setTransferRemark("test");

        request.setTransferDetailList(new ArrayList<>());
        request.getTransferDetailList().add(transferDetail);

        transferService.initiateBatchTransfer(request);
    }

}
