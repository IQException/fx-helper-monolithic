package com.iqexception.fxhelper.controller.dto.composite;

import com.iqexception.fxhelper.controller.dto.order.OrderDetail;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxOrder;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxShop;

public class UserOrderDetail extends OrderDetail {

    private String shopName;
    private String shopLogo;

    public UserOrderDetail(FxOrder order, FxShop shop) {
        this.setId(order.getId());
        this.setShopId(order.getShopId());
        this.setUserId(order.getUserId());
        this.shopName = shop.getShopName();
        this.shopLogo = shop.getLogo();
        this.setCapture(order.getCapture());
        this.setAmount(order.getAmount());
        this.setFxTime(order.getFxTime());
        this.setStatus(order.getStatus());
        this.setFailMsg(order.getFailMsg());
        this.setCreatedAt(order.getCreatedAt());
        this.setUpdatedAt(order.getUpdatedAt());
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLogo() {
        return shopLogo;
    }

    public void setShopLogo(String shopLogo) {
        this.shopLogo = shopLogo;
    }
}
