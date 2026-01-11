package com.iqexception.fxhelper.controller.dto.order;

public class OrderCreateResult {

    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderCreateResult(Long orderId) {
        this.orderId = orderId;
    }
}
