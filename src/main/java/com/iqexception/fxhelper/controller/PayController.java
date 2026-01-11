package com.iqexception.fxhelper.controller;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.pay.*;
import com.iqexception.fxhelper.service.PayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/pay")
public class PayController {

    private final PayService payService;

    public PayController(PayService payService) {
        this.payService = payService;
    }

    @PostMapping("/deposit")
    public Response<DepositResult> deposit(@RequestBody @Valid Request<DepositParam> request) {
        return payService.deposit(request);
    }

    @PostMapping("/withdraw")
    public Response<WithdrawResult> withdraw(@RequestBody @Valid Request<WithdrawParam> request) {
        return payService.withdraw(request);
    }

    @PostMapping("/pay_notify")
    public ResponseEntity<String> payNotify(@RequestBody String body,
                                            @RequestHeader("Wechatpay-Signature") String sign,
                                            @RequestHeader("Wechatpay-Serial") String serial,
                                            @RequestHeader("Wechatpay-Nonce") String nonce,
                                            @RequestHeader("Wechatpay-Timestamp") String timestamp,
                                            @RequestHeader("Wechatpay-Signature-Type") String signType) {

        return payService.payNotify(body, sign, serial, nonce, timestamp, signType);


    }


}
