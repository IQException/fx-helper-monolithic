package com.iqexception.fxhelper.controller.dto.composite;

import jakarta.validation.constraints.Positive;

public class GetShopParam {
    @Positive
    private Long shopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
