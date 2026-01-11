package com.iqexception.fxhelper.controller.dto.shop;

import jakarta.validation.constraints.NotBlank;

public class ShopCreateParam {

    @NotBlank
    private String address;
    @NotBlank
    private String name;
    @NotBlank
    private String logo;
    //FIXME @Pattern
    @NotBlank
    private String phone;
    @NotBlank
    private String intro;
    @NotBlank
    private String paySecret;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPaySecret() {
        return paySecret;
    }

    public void setPaySecret(String paySecret) {
        this.paySecret = paySecret;
    }
}
