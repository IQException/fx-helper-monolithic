package com.iqexception.fxhelper.message.wxmsg.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WithdrawMessage {
    @JsonProperty("thing19")
    private ValueObject shopName;
    @JsonProperty("amount5")
    private ValueObject amount;
    @JsonProperty("time32")
    private ValueObject createTime;
    @JsonProperty("character_string29")
    private ValueObject orderNo;
    @JsonProperty("thing25")
    private ValueObject orderType;

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

    public ValueObject getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ValueObject createTime) {
        this.createTime = createTime;
    }

    public ValueObject getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(ValueObject orderNo) {
        this.orderNo = orderNo;
    }

    public ValueObject getOrderType() {
        return orderType;
    }

    public void setOrderType(ValueObject orderType) {
        this.orderType = orderType;
    }
}
