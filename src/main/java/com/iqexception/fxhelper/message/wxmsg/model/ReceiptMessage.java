package com.iqexception.fxhelper.message.wxmsg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReceiptMessage {

    @JsonProperty("thing2")
    private ValueObject shopName;
    @JsonProperty("amount1")
    private ValueObject amount;
    @JsonProperty("time5")
    private ValueObject payTime;
    @JsonProperty("character_string3")
    private ValueObject orderNo;

    public ValueObject getShopName() {
        return shopName;
    }

    public void setShopName(ValueObject shopName) {
        this.shopName = shopName;
    }

    public ValueObject getAmount() {
        return amount;
    }

    public void setAmount(ValueObject amount) {
        this.amount = amount;
    }

    public ValueObject getPayTime() {
        return payTime;
    }

    public void setPayTime(ValueObject payTime) {
        this.payTime = payTime;
    }

    public ValueObject getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(ValueObject orderNo) {
        this.orderNo = orderNo;
    }
}
