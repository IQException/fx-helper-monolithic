package com.iqexception.fxhelper.message.wxmsg;

import com.iqexception.fxhelper.dal.ext.FxWxMsgSubsExtDao;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxWxMsgSubs;
import com.iqexception.fxhelper.message.wxmsg.model.BillMessage;
import com.iqexception.fxhelper.message.wxmsg.model.ReceiptMessage;
import com.iqexception.fxhelper.message.wxmsg.model.ValueObject;
import com.iqexception.fxhelper.service.impl.BaseService;
import com.iqexception.fxhelper.support.Constants;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.util.MessageUtil;
import com.iqexception.fxhelper.wx.WxApi;
import com.iqexception.fxhelper.wx.WxConstants;
import com.iqexception.fxhelper.wx.model.CommonResponse;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class WxMsgService extends BaseService {

    private static final Logger LOG = LoggerFactory.getLogger(WxMsgService.class);

    private static final String BILL_MESSAGE_ID = "tXNxjN_zMuK-ud_Ai7mhp6Jy7YDbS6XRd6I6I0wpoPg";

    private static final String RECEIPT_MESSAGE_ID = "cz-dFqQOL9GgaNA0xc44aD0Y57m6QlsilCXykHrPeSk";

    private final FxWxMsgSubsExtDao wxMsgSubsExtDao;

    public WxMsgService(MessageSource messageSource,
                        WxApi wxApi,
                        DSLContext jooqClient,
                        JsonMapper jsonMapper,
                        FxWxMsgSubsExtDao wxMsgSubsExtDao) {
        super(messageSource, wxApi, jooqClient, jsonMapper);
        this.wxMsgSubsExtDao = wxMsgSubsExtDao;
    }

    public void sendBillMessage(BillType billType,
                                Long shopId,
                                String shopName,
                                String openId,
                                BigDecimal amount,
                                Long orderNo,
                                LocalDateTime createTime) {


        FxWxMsgSubs msgSubs = wxMsgSubsExtDao.fetchByKey(openId, BILL_MESSAGE_ID);
        if (msgSubs == null || msgSubs.getCount() <= 0) {
            LOG.warn(MessageUtil.message(ErrorCode.MESSAGE_NOT_SUBSCRIBED, messageSource));
            return;
        }

        BillMessage message = new BillMessage();
        message.setShopName(new ValueObject(shopName));
        message.setAmount(new ValueObject("￥" + amount.setScale(2, RoundingMode.DOWN)));
        message.setCreateTime(new ValueObject(createTime.format(Constants.BASIC_FORMATTER)));
        message.setOrderNo(new ValueObject(orderNo.toString()));
        message.setBillType(new ValueObject(billType.getVal()));

        CommonResponse response = wxApi.sendMessage(BILL_MESSAGE_ID, openId, WxConstants.PAGE_SHOP_DETAIL + "?shopId=" + shopId, message);

        LOG.info(jsonMapper.serialize(response));

        if (response.getErrorCode() != 0) {
            LOG.warn(jsonMapper.serialize(response));
        }
        wxMsgSubsExtDao.decrCount(msgSubs.getId());


    }

    public void sendReceiptMessage(String shopName,
                                   String openId,
                                   BigDecimal amount,
                                   Long orderNo,
                                   LocalDateTime payTime) {


        FxWxMsgSubs msgSubs = wxMsgSubsExtDao.fetchByKey(openId, RECEIPT_MESSAGE_ID);
        if (msgSubs == null || msgSubs.getCount() <= 0) {
            LOG.warn(MessageUtil.message(ErrorCode.MESSAGE_NOT_SUBSCRIBED, messageSource));
            return;
        }

        ReceiptMessage message = new ReceiptMessage();
        message.setShopName(new ValueObject(shopName));
        message.setAmount(new ValueObject("￥" + amount.setScale(2, RoundingMode.DOWN)));
        message.setPayTime(new ValueObject(payTime.format(Constants.BASIC_FORMATTER)));
        message.setOrderNo(new ValueObject(orderNo.toString()));

        CommonResponse response = wxApi.sendMessage(RECEIPT_MESSAGE_ID, openId,
                WxConstants.PAGE_CONSUMER_ORDER_LIST, message);

        if (response.getErrorCode() != 0) {
            LOG.warn(jsonMapper.serialize(response));
        }
        wxMsgSubsExtDao.decrCount(msgSubs.getId());


    }

}
