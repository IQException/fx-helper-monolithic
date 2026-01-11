package com.iqexception.fxhelper.controller;

import com.iqexception.fxhelper.controller.dto.BaseRequest;
import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.shop.*;
import com.iqexception.fxhelper.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/create")
    public Response<ShopCreateResult> create(@RequestBody @Valid Request<ShopCreateParam> request) {
        return shopService.create(request);
    }

    @PostMapping("/update")
    public BaseResponse update(@RequestBody @Valid Request<ShopUpdateParam> request) {
        return shopService.update(request);
    }

    @PostMapping("/get_public_info")
    public Response<GetPublicShopInfoResult> getPublicInfo(
            @RequestBody @Valid Request<GetPublicShopInfoParam> request) {
        return shopService.getPublicInfo(request);
    }

    @PostMapping("/query")
    public Response<ShopQueryResult> query(@RequestBody @Valid Request<ShopQueryParam> request) {
        return shopService.query(request);
    }

    @PostMapping("/list")
    public Response<ShopListResult> list(@RequestBody @Valid BaseRequest request) {
        return shopService.list(request);
    }

    @PostMapping("/switch")
    public Response<SwitchResult> switchFx(@RequestBody @Valid Request<SwitchParam> request) {
        return shopService.switchFx(request);
    }

    @PostMapping("/get_qr_codes")
    public Response<GetQrCodesResult> getQrCodes(@RequestBody @Valid Request<GetQrCodesParam> request) {
        return shopService.getQrCodes(request);
    }


}
