package com.iqexception.fxhelper.service.impl;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.order.OrderCreateParam;
import com.iqexception.fxhelper.controller.dto.order.OrderCreateResult;
import com.iqexception.fxhelper.controller.dto.order.OrderPayParam;
import com.iqexception.fxhelper.controller.dto.order.OrderPayResult;
import com.iqexception.fxhelper.dal.ext.*;
import com.iqexception.fxhelper.dal.generator.tables.pojos.*;
import com.iqexception.fxhelper.message.wxmsg.WxMsgService;
import com.iqexception.fxhelper.service.OrderService;
import com.iqexception.fxhelper.service.PayService;
import com.iqexception.fxhelper.service.builder.OrderBuilder;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.support.OrderStatus;
import com.iqexception.fxhelper.support.ShopStatus;
import com.iqexception.fxhelper.support.TLVarManager;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.wx.WxApi;
import org.jooq.DSLContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl extends BaseService implements OrderService {

    private final FxOrderExtDao orderExtDao;

    private final FxShopExtDao shopExtDao;

    private final FxUserExtDao userExtDao;

    private final FxAccountExtDao accountExtDao;

    private final FxSerialNoExtDao serialNoExtDao;

    private final PayService payService;

    private final WxMsgService wxMsgService;

    public OrderServiceImpl(MessageSource messageSource,
                            WxApi wxApi,
                            DSLContext jooqClient,
                            JsonMapper jsonMapper,
                            FxOrderExtDao orderExtDao,
                            FxShopExtDao shopExtDao,
                            FxUserExtDao userExtDao,
                            FxAccountExtDao accountExtDao,
                            FxSerialNoExtDao serialNoExtDao,
                            PayService payService,
                            WxMsgService wxMsgService) {
        super(messageSource, wxApi, jooqClient, jsonMapper);
        this.orderExtDao = orderExtDao;
        this.shopExtDao = shopExtDao;
        this.userExtDao = userExtDao;
        this.accountExtDao = accountExtDao;
        this.serialNoExtDao = serialNoExtDao;
        this.payService = payService;
        this.wxMsgService = wxMsgService;
    }

    @Override
    public Response<OrderCreateResult> create(Request<OrderCreateParam> request) {

        FxShop shop = shopExtDao.fetchOneByShopId(request.getParam().getShopId());
        if (shop == null)
            return new Response<>(status(ErrorCode.SHOP_NOT_EXIST));
        if (shop.getStatus() == ShopStatus.CLOSED.getVal())
            return new Response<>(status(ErrorCode.SHOP_CLOSED));

        FxSerialNo serialNo = serialNoExtDao.fetchOne(request.getParam().getShopId(), request.getParam().getSerialNo());
        if (serialNo == null)
            return new Response<>(status(ErrorCode.ORDER_NOT_EXIST));

        FxOrder order = orderExtDao.fetchOne(request.getParam().getShopId(), request.getParam().getSerialNo());
        if (order != null)
            return new Response<>(status(ErrorCode.ORDER_DUPLICATE));

        order = OrderBuilder.build(request.getParam());

        orderExtDao.insert(order);

        return new Response<>(new OrderCreateResult(order.getId()));
    }

    @Override
    public Response<OrderPayResult> pay(Request<OrderPayParam> request) {


        FxOrder order = orderExtDao.fetchOneById(request.getParam().getOrderId());
        if (order == null)
            return new Response<>(status(ErrorCode.PARAM_ERROR));

        FxShop shop = shopExtDao.fetchOneByShopId(order.getShopId());
        if (shop.getStatus() == ShopStatus.CLOSED.getVal())
            return new Response<>(status(ErrorCode.SHOP_CLOSED));

        FxUser user = userExtDao.fetchOneByUserId(order.getUserId());

        FxAccount shopOwner = accountExtDao.fetchOneByUserId(TLVarManager.getCurrentUser().getUserId());

       FxPayInfo payInfo=  payService.acct2wx(shopOwner.getAccountId(), user.getOpenId(), order.getAmount());
        //FIXME 暂时默认都成功
        orderExtDao.updateStatus(order.getId(), OrderStatus.SUCCEED.getVal());


        wxMsgService.sendReceiptMessage(
                shop.getShopName(),
                user.getOpenId(), order.getAmount(),
                payInfo.getId(), LocalDateTime.now());

        return new Response<>();
    }


}
