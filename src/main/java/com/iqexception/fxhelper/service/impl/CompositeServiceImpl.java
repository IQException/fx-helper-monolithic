package com.iqexception.fxhelper.service.impl;

import com.iqexception.fxhelper.controller.dto.Request;
import com.iqexception.fxhelper.controller.dto.Response;
import com.iqexception.fxhelper.controller.dto.composite.*;
import com.iqexception.fxhelper.dal.ext.FxAccountExtDao;
import com.iqexception.fxhelper.dal.ext.FxOrderExtDao;
import com.iqexception.fxhelper.dal.ext.FxShopExtDao;
import com.iqexception.fxhelper.dal.ext.FxUserExtDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxAccount;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxOrder;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxShop;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxUser;
import com.iqexception.fxhelper.service.CompositeService;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.support.OrderStatus;
import com.iqexception.fxhelper.support.TLVarManager;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.wx.WxApi;
import org.jooq.DSLContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CompositeServiceImpl extends BaseService implements CompositeService {

    private final FxUserExtDao userExtDao;

    private final FxOrderExtDao orderExtDao;

    private final FxShopExtDao shopExtDao;

    private final FxAccountExtDao accountExtDao;


    public CompositeServiceImpl(MessageSource messageSource,
                                WxApi wxApi,
                                DSLContext jooqClient,
                                JsonMapper jsonMapper,
                                FxUserExtDao userExtDao,
                                FxOrderExtDao orderExtDao,
                                FxShopExtDao shopExtDao,
                                FxAccountExtDao accountExtDao) {
        super(messageSource, wxApi, jooqClient, jsonMapper);
        this.userExtDao = userExtDao;
        this.orderExtDao = orderExtDao;
        this.shopExtDao = shopExtDao;
        this.accountExtDao = accountExtDao;
    }

    @Override
    public Response<ShopOrderListResult> listShopOrders(Request<ShopOrderListParam> request) {
        FxShop shop = shopExtDao.fetchOneByShopId(request.getParam().getShopId());
        if (shop == null
                || !shop.getOwnerUserId().equals(TLVarManager.getCurrentUser().getUserId()))
            return new Response<>(status(ErrorCode.PARAM_ERROR));

        ShopOrderListParam param = request.getParam();
        List<FxOrder> orderList = orderExtDao.fetch(
                param.getShopId(),
                null,
                param.getStatus(),
                param.getFrom(),
                param.getTo(),
                param.getOffset(),
                param.getLimit());

        List<Long> userIds = orderList.stream().map(FxOrder::getUserId)
                .distinct().collect(Collectors.toList());

        if (!userIds.isEmpty()) {

            Map<Long, FxUser> userMap = userExtDao.fetchByUserId(userIds.toArray(new Long[0]))
                    .stream().collect(Collectors.toMap(FxUser::getUserId, e -> e));

            return new Response<>(new ShopOrderListResult(
                    orderList.stream().map(e -> new ShopOrderDetail(e, userMap.get(e.getUserId())))
                            .collect(Collectors.toList())));
        }
        return new Response<>();


    }

    @Override
    public Response<GetShopResult> getShop(Request<GetShopParam> request) {
        FxShop shop = shopExtDao.fetchOneByShopId(request.getParam().getShopId());

        Long userId = TLVarManager.getCurrentUser().getUserId();
        if (shop == null
                || !userId.equals(shop.getOwnerUserId()))
            return new Response<>(status(ErrorCode.PARAM_ERROR));

        FxAccount account = accountExtDao.fetchOneByUserId(userId);

        int orderCount =  orderExtDao.count(shop.getShopId(),OrderStatus.SUCCEED.getVal());

        BigDecimal orderTotalAmount = orderExtDao.totalAmount(shop.getShopId(),OrderStatus.SUCCEED.getVal());

        return new Response<>(new GetShopResult(shop, account, orderCount, orderTotalAmount));

    }

    @Override
    public Response<UserOrderListResult> listUserOrders(Request<UserOrderListParam> request) {


        UserOrderListParam param = request.getParam();
        List<FxOrder> orderList = orderExtDao.fetch(
                null,
                TLVarManager.getCurrentUser().getUserId(),
                param.getStatus(),
                param.getFrom(),
                param.getTo(),
                param.getOffset(),
                param.getLimit());

        List<Long> shopIds = orderList.stream().map(FxOrder::getShopId)
                .distinct().collect(Collectors.toList());

        if (!shopIds.isEmpty()) {

            Map<Long, FxShop> shopMap = shopExtDao.fetchByShopId(shopIds.toArray(new Long[0]))
                    .stream().collect(Collectors.toMap(FxShop::getShopId, e -> e));

            return new Response<>(new UserOrderListResult(
                    orderList.stream().map(e->new UserOrderDetail(e,shopMap.get(e.getShopId())))
                            .collect(Collectors.toList())));
        }

        return new Response<>();

    }
}
