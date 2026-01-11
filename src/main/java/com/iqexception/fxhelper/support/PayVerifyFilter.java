package com.iqexception.fxhelper.support;

import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.service.UserService;
import com.iqexception.fxhelper.util.ErrorCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayVerifyFilter extends BaseFilter implements Filter {

    private final UserService userService;

    public PayVerifyFilter(MessageSource messageSource, JsonMapper jsonMapper, UserService userService) {
        super(messageSource, jsonMapper);
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");

        String secret = req.getHeader(Constants.HEADER_PAY_SECRET);

        if (StringUtils.isBlank(secret) || !verify(secret)) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatus(buildRespStatus(ErrorCode.PASSWORD_ERROR));
            res.getWriter().write(jsonMapper.serialize(baseResponse));
            res.getWriter().flush();
            return;
        }
        chain.doFilter(request, response);
    }

    private boolean verify(String secret) {
        return userService.verifyPaySecret(TLVarManager.getCurrentUser().getUserId(), secret);
    }
}
