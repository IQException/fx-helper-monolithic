package com.iqexception.fxhelper.service.impl;

import com.iqexception.fxhelper.controller.dto.ResponseStatus;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.util.MessageUtil;
import com.iqexception.fxhelper.wx.WxApi;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

public class BaseService {

    protected final Logger LOG = LoggerFactory.getLogger(BaseService.class);

    protected MessageSource messageSource;
    protected WxApi wxApi;
    protected JsonMapper jsonMapper;
    protected DSLContext jooqClient;

    protected static final String BIZ_ID_SEPARATOR_ACCOUNT = "_ACCOUNT_";

    protected static final String BIZ_ID_SEPARATOR_WX = "_WX_";


    public BaseService(
            MessageSource messageSource,
            WxApi wxApi,
            DSLContext jooqClient,
            JsonMapper jsonMapper) {
        this.messageSource = messageSource;
        this.wxApi = wxApi;
        this.jooqClient = jooqClient;
        this.jsonMapper = jsonMapper;
    }

    public ResponseStatus status(int errorCode) {
        return new ResponseStatus(String.valueOf(errorCode), MessageUtil.message(errorCode, messageSource));
    }

}
