package com.iqexception.fxhelper.controller.support;

import com.iqexception.fxhelper.controller.dto.HasResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class ResponseStatusLogAdvice implements ResponseBodyAdvice<Object> {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                             MediaType selectedContentType,
                                             Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                             ServerHttpRequest request,
                                             ServerHttpResponse response) {
        if (body == null) return null;

        if(!HasResponseStatus.class.isAssignableFrom(body.getClass()))
            return body;

        HasResponseStatus _body = (HasResponseStatus)body;
        if (_body.getStatus().getErrorCode().startsWith("900")) {
            LOG.error("handler:{}#{},errorCode:{},errorMessage:{}",
                    returnType.getClass(),
                    returnType.getMethod(),
                    _body.getStatus().getErrorCode(),
                    _body.getStatus().getErrorMessage());
        } else if (Long.parseLong(_body.getStatus().getErrorCode()) != 0) {
            LOG.warn("handler:{}#{}, errorCode:{},errorMessage:{}",
                    returnType.getClass(),
                    returnType.getMethod(),
                    _body.getStatus().getErrorCode(),
                    _body.getStatus().getErrorMessage());
        }
        return body;
    }
}
