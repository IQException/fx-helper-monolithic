package com.iqexception.fxhelper.controller.dto.user;

import jakarta.validation.constraints.NotBlank;

public class UserLoginParam {
    @NotBlank
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
