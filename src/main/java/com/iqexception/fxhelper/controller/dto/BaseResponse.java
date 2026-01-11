package com.iqexception.fxhelper.controller.dto;

public class BaseResponse implements HasResponseStatus {

    private ResponseStatus status = ResponseStatus.success();

    public BaseResponse() {
    }

    public BaseResponse(ResponseStatus status) {
        this.status = status;
    }

    @Override
    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}
