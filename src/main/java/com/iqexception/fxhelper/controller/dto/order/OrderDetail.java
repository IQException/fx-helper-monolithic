package com.iqexception.fxhelper.controller.dto.order;

import com.iqexception.fxhelper.dal.generator.tables.pojos.FxOrder;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDetail {

    @NotNull
    private Long id;
    @NotNull
    private Long shopId;
    @NotNull
    private Long userId;
    @NotNull
    private String capture;
    @NotNull
    private BigDecimal amount;
    private LocalDateTime fxTime;
    @NotNull
    private Integer status;
    private String failMsg;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime updatedAt;

    public OrderDetail() {
    }

    public OrderDetail(FxOrder order) {
        this.id = order.getId();
        this.shopId = order.getShopId();
        this.userId = order.getUserId();
        this.capture = order.getCapture();
        this.amount = order.getAmount();
        this.fxTime = order.getFxTime();
        this.status = order.getStatus();
        this.failMsg = order.getFailMsg();
        this.createdAt = order.getCreatedAt();
        this.updatedAt = order.getUpdatedAt();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCapture() {
        return capture;
    }

    public void setCapture(String capture) {
        this.capture = capture;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getFxTime() {
        return fxTime;
    }

    public void setFxTime(LocalDateTime fxTime) {
        this.fxTime = fxTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
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
