package com.iqexception.fxhelper.support;

import com.iqexception.fxhelper.controller.dto.ResponseStatus;
import com.iqexception.fxhelper.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

public class BaseFilter {

    protected  MessageSource messageSource;

    protected  JsonMapper jsonMapper;

    protected Logger LOG = LoggerFactory.getLogger(getClass());

    public BaseFilter(MessageSource messageSource, JsonMapper jsonMapper) {
        this.messageSource = messageSource;
        this.jsonMapper = jsonMapper;
    }

    public BaseFilter() {
    }

    protected ResponseStatus buildRespStatus(int errorCode) {
        return new ResponseStatus(String.valueOf(errorCode),
                MessageUtil.message(errorCode, messageSource));
    }
}
