package com.iqexception.fxhelper.controller.dto.shop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ShopUpdateParam {
    @Positive
    private Long shopId;
    @NotBlank
    private String address;
    @NotBlank
    private String name;
    @NotBlank
    private String logo;
    @NotBlank
    private String phone;
    @NotBlank
    private String intro;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

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
}
