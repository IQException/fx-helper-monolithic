package com.iqexception.fxhelper.controller.dto.composite;

import java.util.ArrayList;
import java.util.List;

public class UserOrderListResult {
    private List<UserOrderDetail> orderList = new ArrayList<>();

    public UserOrderListResult() {
    }

    public UserOrderListResult(List<UserOrderDetail> orderList) {
        this.orderList = orderList;
    }

    public List<UserOrderDetail> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<UserOrderDetail> orderList) {
        this.orderList = orderList;
    }
}
