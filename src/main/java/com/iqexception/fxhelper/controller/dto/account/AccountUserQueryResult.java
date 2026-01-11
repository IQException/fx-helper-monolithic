package com.iqexception.fxhelper.controller.dto.account;

import com.iqexception.fxhelper.dal.generator.tables.pojos.FxAccount;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountUserQueryResult {

    private Long accountId;

    private Long userId;

    private BigDecimal balance;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public AccountUserQueryResult(FxAccount value) {
        this.accountId = value.getAccountId();
        this.userId = value.getUserId();
        this.balance = value.getBalance();
        this.status = value.getStatus();
        this.createdAt = value.getCreatedAt();
        this.updatedAt = value.getUpdatedAt();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
