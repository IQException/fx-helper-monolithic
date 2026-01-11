package com.iqexception.fxhelper.controller.dto.pay;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class WithdrawParam {

    // 默认从用户绑定的内部account转到openId
    @Positive
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
