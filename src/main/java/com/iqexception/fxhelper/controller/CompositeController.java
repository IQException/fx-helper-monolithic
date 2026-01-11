package com.iqexception.fxhelper.controller;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.composite.*;
import com.iqexception.fxhelper.service.CompositeService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/composite")
public class CompositeController {

    private final CompositeService compositeService;

    public CompositeController(CompositeService compositeService) {
        this.compositeService = compositeService;
    }

    @PostMapping("/list_shop_orders")
    public Response<ShopOrderListResult> listShopOrders(@RequestBody @Valid Request<ShopOrderListParam> request) {
        return compositeService.listShopOrders(request);
    }

    @PostMapping("list_user_orders")
    public Response<UserOrderListResult> listUserOrders(@RequestBody @Valid Request<UserOrderListParam> request) {
        return compositeService.listUserOrders(request);
    }

    @PostMapping("/get_shop")
    public  Response<GetShopResult> getShop(@RequestBody @Valid Request<GetShopParam> request){
        return compositeService.getShop(request);
    }

}
