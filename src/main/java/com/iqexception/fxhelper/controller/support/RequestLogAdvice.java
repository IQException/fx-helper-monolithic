package com.iqexception.fxhelper.controller.support;

import com.iqexception.fxhelper.controller.dto.HasMobileRequestHead;
import com.iqexception.fxhelper.support.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

@ControllerAdvice
public class RequestLogAdvice implements RequestBodyAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(RequestLogAdvice.class);

    private final JsonMapper jsonMapper;

    public RequestLogAdvice(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        Class[] paramTypes = methodParameter.getMethod().getParameterTypes();
        return paramTypes.length == 1 && HasMobileRequestHead.class.isAssignableFrom(paramTypes[0]);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        LOG.info("request:{}", jsonMapper.serialize(body));
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
