package com.iqexception.fxhelper.support;

public enum PayChannel {
    WX(0),ACCOUNT(1);

    private int val;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    PayChannel(int val) {
        this.val = val;
    }
}
