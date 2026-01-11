package com.iqexception.fxhelper.controller.dto.shop;

import java.util.ArrayList;
import java.util.List;

public class ShopListResult {

    private List<ShopDetail> shopList = new ArrayList<>();

    public ShopListResult() {
    }

    public List<ShopDetail> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopDetail> shopList) {
        this.shopList = shopList;
    }

    public ShopListResult(List<ShopDetail> shopList) {
        this.shopList = shopList;
    }
}
