package com.iqexception.fxhelper.message.wxmsg;

public enum BillType {
    DEPOSIT("充值"),WITHDRAW("提现"),TRANSFER("转账");
    private String val;

    BillType(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
