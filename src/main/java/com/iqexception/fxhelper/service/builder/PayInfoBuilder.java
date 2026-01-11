package com.iqexception.fxhelper.service.builder;

import com.iqexception.fxhelper.dal.generator.tables.pojos.FxPayInfo;
import com.iqexception.fxhelper.support.PayChannel;

import java.math.BigDecimal;

public class PayInfoBuilder {
    public static final String BIZ_ID_SEPARATOR_ACCOUNT = "_ACCOUNT_";

    public static final String BIZ_ID_SEPARATOR_WX = "_WX_";

    public static FxPayInfo build(BigDecimal amount,
                                  Long fromAccountId,
                                  String toOpenId) {
        FxPayInfo payInfo = new FxPayInfo();
        payInfo.setAmount(amount);
        payInfo.setFromAccount(fromAccountId.toString());
        payInfo.setFromChannel(PayChannel.ACCOUNT.getVal());
        payInfo.setToAccount(toOpenId);
        payInfo.setToChannel(PayChannel.WX.getVal());
        payInfo.setBizId(fromAccountId + BIZ_ID_SEPARATOR_ACCOUNT + System.currentTimeMillis());

        return payInfo;
    }

    public static FxPayInfo build(BigDecimal amount,
                                  String fromOpenId,
                                  Long toAccountId) {
        FxPayInfo payInfo = new FxPayInfo();
        payInfo.setAmount(amount);
        payInfo.setFromAccount(fromOpenId);
        payInfo.setFromChannel(PayChannel.WX.getVal());
        payInfo.setToAccount(toAccountId.toString());
        payInfo.setToChannel(PayChannel.ACCOUNT.getVal());
        payInfo.setBizId(fromOpenId + BIZ_ID_SEPARATOR_WX + System.currentTimeMillis());

        return payInfo;
    }
}
