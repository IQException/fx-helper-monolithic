package com.iqexception.fxhelper.service;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.composite.*;

public interface CompositeService {
    Response<ShopOrderListResult> listShopOrders(Request<ShopOrderListParam> request);

    Response<GetShopResult> getShop(Request<GetShopParam> request);

    Response<UserOrderListResult> listUserOrders(Request<UserOrderListParam> request);
}
