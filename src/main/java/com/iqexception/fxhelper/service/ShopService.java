package com.iqexception.fxhelper.service;

import com.iqexception.fxhelper.controller.dto.BaseRequest;
import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.shop.*;

public interface ShopService{
    Response<ShopCreateResult> create(Request<ShopCreateParam> request);

    BaseResponse update(Request<ShopUpdateParam> request);

    Response<ShopQueryResult> query(Request<ShopQueryParam> request);

    Response<ShopListResult> list(BaseRequest request);

    Response<SwitchResult> switchFx(Request<SwitchParam> request);

    Response<GetQrCodesResult> getQrCodes(Request<GetQrCodesParam> request);

    Response<GetPublicShopInfoResult> getPublicInfo(Request<GetPublicShopInfoParam> request);
}
