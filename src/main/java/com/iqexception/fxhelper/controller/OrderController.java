package com.iqexception.fxhelper.controller;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.order.OrderCreateParam;
import com.iqexception.fxhelper.controller.dto.order.OrderCreateResult;
import com.iqexception.fxhelper.controller.dto.order.OrderPayParam;
import com.iqexception.fxhelper.controller.dto.order.OrderPayResult;
import com.iqexception.fxhelper.service.OrderService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Response<OrderCreateResult> create(@RequestBody @Valid Request<OrderCreateParam> request) {
        return orderService.create(request);
    }

    @PostMapping("/pay")
    public Response<OrderPayResult> pay(@RequestBody @Valid Request<OrderPayParam> request) {
        return orderService.pay(request);
    }
}
