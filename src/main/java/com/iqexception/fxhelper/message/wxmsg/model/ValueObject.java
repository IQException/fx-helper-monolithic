package com.iqexception.fxhelper.message.wxmsg.model;

public class ValueObject {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ValueObject(String value) {
        this.value = value;
    }
}
