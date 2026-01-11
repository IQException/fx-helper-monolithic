package com.iqexception.fxhelper.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Code2SessionResponse extends CommonResponse {
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("openid")
    private String openId;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
