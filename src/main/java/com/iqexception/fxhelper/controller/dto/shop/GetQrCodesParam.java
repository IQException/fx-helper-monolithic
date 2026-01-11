package com.iqexception.fxhelper.controller.dto.shop;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

public class GetQrCodesParam {

    @Max(10)
    @Positive
    private Integer number;
    @Positive
    private Long shopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
