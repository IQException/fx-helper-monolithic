package com.iqexception.fxhelper.controller.dto.user;

public class UserLoginResult {

    private Long userId;

    private String openId;

    private Boolean newUser = false;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserLoginResult() {
    }

    public UserLoginResult(Long userId, String openId) {
        this.userId = userId;
        this.openId = openId;
    }
}
