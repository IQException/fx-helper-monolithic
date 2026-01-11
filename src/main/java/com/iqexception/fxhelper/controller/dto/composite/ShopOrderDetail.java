package com.iqexception.fxhelper.controller.dto.composite;

import com.iqexception.fxhelper.controller.dto.order.OrderDetail;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxOrder;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxUser;

public class ShopOrderDetail extends OrderDetail {

    private String nickName;
    private String avatar;


    public ShopOrderDetail() {
    }

    public ShopOrderDetail(FxOrder order, FxUser user) {
        this.setId(order.getId());
        this.setShopId(order.getShopId());
        this.setUserId(order.getUserId());
        this.nickName = user.getNickName();
        this.avatar = user.getAvatar();
        this.setCapture(order.getCapture());
        this.setAmount(order.getAmount());
        this.setFxTime(order.getFxTime());
        this.setStatus(order.getStatus());
        this.setFailMsg(order.getFailMsg());
        this.setCreatedAt(order.getCreatedAt());
        this.setUpdatedAt(order.getUpdatedAt());
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
