package com.iqexception.fxhelper.service.impl;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.iqexception.fxhelper.controller.dto.*;
import com.iqexception.fxhelper.controller.dto.shop.*;
import com.iqexception.fxhelper.dal.ext.FxSerialNoExtDao;
import com.iqexception.fxhelper.dal.ext.FxShopExtDao;
import com.iqexception.fxhelper.dal.ext.FxUserExtDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxSerialNo;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxShop;
import com.iqexception.fxhelper.service.ShopService;
import com.iqexception.fxhelper.service.builder.ShopBuilder;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.support.TLVarManager;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.wx.WxApi;
import com.iqexception.fxhelper.wx.WxConstants;
import org.jooq.DSLContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl extends BaseService implements ShopService {

    private final FxShopExtDao shopExtDao;

    private final FxUserExtDao userExtDao;

    private final FxSerialNoExtDao serialNoExtDao;

    public ShopServiceImpl(MessageSource messageSource,
                           WxApi wxApi,
                           DSLContext jooqClient,
                           JsonMapper jsonMapper,
                           FxShopExtDao shopExtDao,
                           FxUserExtDao userExtDao,
                           FxSerialNoExtDao serialNoExtDao) {
        super(messageSource, wxApi, jooqClient, jsonMapper);
        this.shopExtDao = shopExtDao;
        this.userExtDao = userExtDao;
        this.serialNoExtDao = serialNoExtDao;

    }


    @Override
    public Response<ShopCreateResult> create(Request<ShopCreateParam> request) {

        FxShop shop = ShopBuilder.build(
                TLVarManager.getCurrentUser().getUserId(), request.getParam());

        jooqClient.transaction(conf -> {

            shopExtDao.insert(shop);
            // 支付密码暂放在用户域
            userExtDao.updatePaySecret(TLVarManager.getCurrentUser().getUserId(),
                    request.getParam().getPaySecret());

        });
        return new Response<>(new ShopCreateResult(shop.getShopId()));
    }

    @Override
    public BaseResponse update(Request<ShopUpdateParam> request) {

        FxShop shop = shopExtDao.fetchOneByShopId(request.getParam().getShopId());

        if (shop == null
                || !TLVarManager.getCurrentUser().getUserId().equals(shop.getOwnerUserId()))
            return new BaseResponse(status(ErrorCode.PARAM_ERROR));

        ShopBuilder.build(shop, request.getParam());
        shopExtDao.update(shop);

        return new BaseResponse();
    }

    @Override
    public Response<ShopQueryResult> query(Request<ShopQueryParam> request) {

        FxShop shop = shopExtDao.fetchOneByShopId(request.getParam().getShopId());

        if (shop == null
                || !TLVarManager.getCurrentUser().getUserId().equals(shop.getOwnerUserId()))
            return new Response<>(status(ErrorCode.PARAM_ERROR));

        return new Response<>(new ShopQueryResult(shop));
    }

    @Override
    public Response<ShopListResult> list(BaseRequest request) {

        List<FxShop> shops = shopExtDao.fetchByOwnerUserId(TLVarManager.getCurrentUser().getUserId());

        return new Response<>(
                new ShopListResult(
                        shops.stream()
                                .map(ShopDetail::new)
                                .collect(Collectors.toList())));
    }

    @Override
    public Response<SwitchResult> switchFx(Request<SwitchParam> request) {

        ResponseStatus status = validateShopAndOwner(request.getParam().getShopId(),
                TLVarManager.getCurrentUser().getUserId());
        if (status != null) return new Response<>(status);

        shopExtDao.updateStatus(request.getParam().getShopId(),
                request.getParam().getSwitchValue());

        return new Response<>();
    }

    @Override
    public Response<GetQrCodesResult> getQrCodes(Request<GetQrCodesParam> request) {

        ResponseStatus status = validateShopAndOwner(request.getParam().getShopId(),
                TLVarManager.getCurrentUser().getUserId());
        if (status != null) return new Response<>(status);

        List<FxSerialNo> serialNos = Lists.newArrayList();

        for (int i = 0; i < request.getParam().getNumber(); i++) {

            FxSerialNo serialNo = new FxSerialNo();
            serialNo.setShopId(request.getParam().getShopId());
            byte[] hashBytes = Hashing.crc32().hashBytes(
                    UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)).asBytes();
            serialNo.setSerialNo(new String(Base64.getEncoder().encode(hashBytes), StandardCharsets.UTF_8));
            serialNos.add(serialNo);
        }

        serialNoExtDao.insert(serialNos);

        List<String> qrCodes = wxApi.getQrCodes(WxConstants.PAGE_CONSUMER_LANDING,
                serialNos.stream().map(e -> request.getParam().getShopId() + "#" + e.getSerialNo())
                        .collect(Collectors.toList()));

        return new Response<>(new GetQrCodesResult(qrCodes));
    }

    @Override
    public Response<GetPublicShopInfoResult> getPublicInfo(Request<GetPublicShopInfoParam> request) {


        FxShop shop = shopExtDao.fetchOneByShopId(request.getParam().getShopId());

        if (shop == null)
            return new Response<>(status(ErrorCode.PARAM_ERROR));
        GetPublicShopInfoResult result = new GetPublicShopInfoResult();
        result.setAddress(shop.getAddress());
        result.setLogo(shop.getLogo());
        result.setShopName(shop.getShopName());
        result.setIntro(shop.getIntro());
        return new Response<>(result);
    }

    private ResponseStatus validateShopAndOwner(Long shopId, Long userId) {

        FxShop shop = shopExtDao.fetchOneByShopId(shopId);
        if (shop == null
                || !shop.getOwnerUserId().equals(userId))
            return status(ErrorCode.PARAM_ERROR);

        return null;
    }

}
