package com.iqexception.fxhelper.controller.dto.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class AccountTransferParam {
    @NotBlank
    private String secretKey;
    @Positive
    private Long toAccountId;
    @Positive
    private Long fromAccountId;
    @Positive
    private BigDecimal amount;

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }



    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountTransferParam() {
    }

    public AccountTransferParam(Long toAccountId, BigDecimal amount) {
        this.toAccountId = toAccountId;
        this.amount = amount;
    }
}
