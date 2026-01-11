package com.iqexception.fxhelper.controller.dto.order;

import jakarta.validation.constraints.Positive;

public class OrderPayParam {
    @Positive
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
