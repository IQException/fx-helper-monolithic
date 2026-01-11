package com.iqexception.fxhelper.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageRequest {

    @JsonProperty("template_id")
    private String templateId;
    private String page;
    @JsonProperty("touser")
    private String toUser;
    private Object data;
    @JsonProperty("miniprogram_state")
    private String mpState;
    private String lang;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMpState() {
        return mpState;
    }

    public void setMpState(String mpState) {
        this.mpState = mpState;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
