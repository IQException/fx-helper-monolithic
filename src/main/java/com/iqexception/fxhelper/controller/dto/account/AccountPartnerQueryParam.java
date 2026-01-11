package com.iqexception.fxhelper.controller.dto.account;

import jakarta.validation.constraints.Positive;

public class AccountPartnerQueryParam {
    @Positive
    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
