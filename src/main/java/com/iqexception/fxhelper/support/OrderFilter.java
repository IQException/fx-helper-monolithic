package com.iqexception.fxhelper.support;

public enum OrderFilter {
    ONE_DAY("24H"),ONE_WEEK("7D");
    private String val;

    OrderFilter(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
