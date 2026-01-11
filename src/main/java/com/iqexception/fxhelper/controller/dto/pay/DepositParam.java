package com.iqexception.fxhelper.controller.dto.pay;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class DepositParam {
    // 默认从openId转到绑定内部account

    @Positive
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
