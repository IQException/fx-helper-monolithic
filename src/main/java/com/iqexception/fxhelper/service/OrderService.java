package com.iqexception.fxhelper.service;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.order.OrderCreateParam;
import com.iqexception.fxhelper.controller.dto.order.OrderCreateResult;
import com.iqexception.fxhelper.controller.dto.order.OrderPayParam;
import com.iqexception.fxhelper.controller.dto.order.OrderPayResult;

public interface OrderService {
    Response<OrderCreateResult> create(Request<OrderCreateParam> request);

    Response<OrderPayResult> pay(Request<OrderPayParam> request);
}
