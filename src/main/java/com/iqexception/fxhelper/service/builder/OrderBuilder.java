package com.iqexception.fxhelper.service.builder;

import com.iqexception.fxhelper.controller.dto.order.OrderCreateParam;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxOrder;
import com.iqexception.fxhelper.support.TLVarManager;

import java.math.BigDecimal;

public class OrderBuilder {

    public static FxOrder build(OrderCreateParam param) {

        FxOrder order = new FxOrder();
        //FIXME 简单起见，金额写死
        order.setAmount(new BigDecimal(1));
        order.setCapture(param.getCapture());
        order.setShopId(param.getShopId());
        order.setUserId(TLVarManager.getCurrentUser().getUserId());
        order.setSerialNo(param.getSerialNo());

        return order;
    }
}
