package com.iqexception.fxhelper.controller.dto.common;

public class SignUploadUrlResult {

    private String signedUrl;

    public SignUploadUrlResult() {
    }

    public String getSignedUrl() {
        return signedUrl;
    }

    public void setSignedUrl(String signedUrl) {
        this.signedUrl = signedUrl;
    }

    public SignUploadUrlResult(String signedUrl) {
        this.signedUrl = signedUrl;
    }
}
