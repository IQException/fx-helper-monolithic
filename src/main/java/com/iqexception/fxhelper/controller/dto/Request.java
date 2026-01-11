package com.iqexception.fxhelper.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class Request<T> implements HasMobileRequestHead {
    @NotNull
    private MobileRequestHead head;
    @Valid
    @NotNull
    private T param;

    @Override
    public MobileRequestHead getHead() {
        return head;
    }

    @Override
    public void setHead(MobileRequestHead head) {
        this.head = head;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }
}
