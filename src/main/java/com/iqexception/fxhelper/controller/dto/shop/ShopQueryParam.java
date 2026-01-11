package com.iqexception.fxhelper.controller.dto.shop;

import jakarta.validation.constraints.Positive;

public class ShopQueryParam {

    @Positive
    private Long shopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
