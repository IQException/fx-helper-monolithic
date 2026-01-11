package com.iqexception.fxhelper.support;

public enum OrderStatus {

    CREATED(0),PROCESSING(1),SUCCEED(2),FAILED(9);

    private int val;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    OrderStatus(int val) {
        this.val = val;
    }
}
