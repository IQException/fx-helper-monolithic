package com.iqexception.fxhelper.controller.dto;

import com.iqexception.fxhelper.util.ErrorCode;

import java.util.Map;

public class ResponseStatus {

    public static ResponseStatus success(){
        return new ResponseStatus(String.valueOf(ErrorCode.SUCCESS));
    }

    public ResponseStatus() {
    }

    public ResponseStatus(String errorCode) {
        this.errorCode = errorCode;
    }

    public ResponseStatus(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private String errorCode;
    private String errorMessage;

    private Map<String,String> errors;

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
