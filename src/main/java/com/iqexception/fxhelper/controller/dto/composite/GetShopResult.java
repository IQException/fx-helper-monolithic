package com.iqexception.fxhelper.controller.dto.composite;

import com.iqexception.fxhelper.dal.generator.tables.pojos.FxAccount;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxShop;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GetShopResult {
    private Long shopId;
    private Long ownerUserId;
    private String shopName;
    private String logo;
    private String intro;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer status;
    private BigDecimal balance;
    private Integer orderCount;
    private BigDecimal orderTotalAmount;

    public GetShopResult(FxShop shop,
                         FxAccount account,
                         Integer orderCount,
                         BigDecimal orderTotalAmount) {
        this.shopId = shop.getShopId();
        this.ownerUserId = shop.getOwnerUserId();
        this.shopName = shop.getShopName();
        this.logo = shop.getLogo();
        this.intro = shop.getIntro();
        this.address = shop.getAddress();
        this.phone = shop.getPhone();
        this.createdAt = shop.getCreatedAt();
        this.updatedAt = shop.getUpdatedAt();
        this.status = shop.getStatus();
        this.balance = account.getBalance();
        this.orderCount = orderCount == null ? 0 : orderCount;
        this.orderTotalAmount = orderTotalAmount == null ? BigDecimal.ZERO : orderTotalAmount;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(BigDecimal orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }
}
