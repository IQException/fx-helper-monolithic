package com.iqexception.fxhelper.support;

public enum ShopStatus {
    CLOSED(0), OPEN(1);
    private int val;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    ShopStatus(int val) {
        this.val = val;
    }
}
