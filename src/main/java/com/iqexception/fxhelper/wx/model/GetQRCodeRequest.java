package com.iqexception.fxhelper.wx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetQRCodeRequest {

    private String scene;

    private String page;
    @JsonProperty("env_version")
    private String envVersion;
    @JsonProperty("check_path")
    private Boolean checkPath;

    public GetQRCodeRequest(String page, String scene) {
        this.scene = scene;
        this.page = page;
    }

    public String getEnvVersion() {
        return envVersion;
    }

    public void setEnvVersion(String envVersion) {
        this.envVersion = envVersion;
    }

    public Boolean getCheckPath() {
        return checkPath;
    }

    public void setCheckPath(Boolean checkPath) {
        this.checkPath = checkPath;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
