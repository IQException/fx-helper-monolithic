package com.iqexception.fxhelper.service;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.pay.DepositParam;
import com.iqexception.fxhelper.controller.dto.pay.DepositResult;
import com.iqexception.fxhelper.controller.dto.pay.WithdrawParam;
import com.iqexception.fxhelper.controller.dto.pay.WithdrawResult;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxPayInfo;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

public interface PayService {
    Response<DepositResult> deposit(Request<DepositParam> request);

    Response<WithdrawResult> withdraw(Request<WithdrawParam> request);

    FxPayInfo acct2wx(Long accountId, String openId, BigDecimal amount);

    ResponseEntity<String> payNotify(String body,
                                     String sign,
                                     String serial,
                                     String nonce,
                                     String timestamp,
                                     String signType);
}
