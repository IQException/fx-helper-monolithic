package com.iqexception.fxhelper.controller.dto.shop;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SwitchParam {

    @Positive
    private Long shopId;
    @Min(0)
    @Max(1)
    @NotNull
    private Integer switchValue;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getSwitchValue() {
        return switchValue;
    }

    public void setSwitchValue(Integer switchValue) {
        this.switchValue = switchValue;
    }
}
