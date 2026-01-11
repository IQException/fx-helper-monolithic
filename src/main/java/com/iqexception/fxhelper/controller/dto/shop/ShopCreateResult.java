package com.iqexception.fxhelper.controller.dto.shop;

public class ShopCreateResult {
    private Long shopId;

    public ShopCreateResult() {
    }

    public ShopCreateResult(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
