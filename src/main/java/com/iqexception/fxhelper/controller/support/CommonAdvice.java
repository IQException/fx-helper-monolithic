package com.iqexception.fxhelper.controller.support;

import com.google.common.base.MoreObjects;
import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.controller.dto.HasResponseStatus;
import com.iqexception.fxhelper.controller.dto.ResponseStatus;
import com.iqexception.fxhelper.util.BizException;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CommonAdvice {

    private final MessageSource messageSource;

    private final Logger LOG = LoggerFactory.getLogger(getClass());


    @Autowired
    public CommonAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public HasResponseStatus handleException(Throwable throwable, HandlerMethod method) {

        BaseResponse response = new BaseResponse();
        response.setStatus(new ResponseStatus());
        int errorCode;
        String errorMessage;
        if (throwable instanceof BizException) {
            errorCode = ErrorCode.SUCCESS == ((BizException) throwable).getErrorCode() ? ErrorCode.SYSTEM_ERROR
                    : ((BizException) throwable).getErrorCode();
            errorMessage = MoreObjects.firstNonNull(throwable.getMessage(),
                    MessageUtil.message(errorCode, messageSource));
            LOG.warn("handler:{}#{},errorCode:{},errorMessage:{}", method.getBean(), method.getMethod(), errorCode, errorMessage);
        } else {
            errorCode = ErrorCode.SYSTEM_ERROR;
            errorMessage = MessageUtil.message(errorCode, messageSource);
            LOG.error("handler:{}#{},errorCode:{},errorMessage:{}", method.getBean(), method.getMethod(), errorCode, errorMessage, throwable);
        }
        response.getStatus().setErrorCode(String.valueOf(errorCode));
        response.getStatus().setErrorMessage(errorMessage);

        return response;
    }

    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HasResponseStatus handleValidationExceptions(MethodArgumentNotValidException ex, HandlerMethod method) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });

        BaseResponse response = new BaseResponse();
        response.setStatus(new ResponseStatus());
        response.getStatus().setErrors(errors);
        LOG.warn("handler:{}#{},param error:{}", method.getBean(), method.getMethod(), errors);
        response.getStatus().setErrorCode(String.valueOf(ErrorCode.PARAM_ERROR));
        response.getStatus().setErrorMessage(MessageUtil.message(ErrorCode.PARAM_ERROR, messageSource));

        return response;
    }
}
