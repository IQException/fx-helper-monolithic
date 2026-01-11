package com.iqexception.fxhelper.util;

public class BizException extends RuntimeException {

    private int errorCode;

    public BizException(String message) {
        super(message);
    }

    public BizException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }


    public BizException() {
    }

    public BizException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
