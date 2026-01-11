package com.iqexception.fxhelper.support;

import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.util.ErrorCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountSignVerifyFilter extends BaseFilter implements Filter {


    public AccountSignVerifyFilter(MessageSource messageSource, JsonMapper jsonMapper) {
        super(messageSource, jsonMapper);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");

        String sign = req.getHeader(Constants.HEADER_ACCOUNT_SIGN);

        if (StringUtils.isBlank(sign) || !verify(sign)) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(buildRespStatus(ErrorCode.SIGN_ERROR));
            res.getWriter().write(jsonMapper.serialize(baseResponse));
            res.getWriter().flush();
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean verify(String sign) {

        //FIXME
        return true;
    }
}
