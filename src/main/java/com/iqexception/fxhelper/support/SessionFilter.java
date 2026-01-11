package com.iqexception.fxhelper.support;

import com.iqexception.fxhelper.controller.dto.BaseResponse;
import com.iqexception.fxhelper.controller.dto.ResponseStatus;
import com.iqexception.fxhelper.util.BizException;
import com.iqexception.fxhelper.util.ErrorCode;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.MessageSource;

import java.io.IOException;

public class SessionFilter extends BaseFilter implements Filter {


    public SessionFilter(MessageSource messageSource, JsonMapper jsonMapper) {

        super(messageSource, jsonMapper);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json");
        res.setCharacterEncoding("utf-8");

        HttpSession session = req.getSession();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(buildRespStatus(ErrorCode.USER_NO_LOGIN));

        if (session == null) {
            res.getWriter().write(jsonMapper.serialize(baseResponse));
            res.getWriter().flush();
            return;
        }
        Long userId = (Long) session.getAttribute(Constants.SESSION_ATTR_USER_ID);
        String openId = (String) session.getAttribute(Constants.SESSION_ATTR_OPEN_ID);
        if (userId == null || StringUtils.isBlank(openId)) {
            res.getWriter().write(jsonMapper.serialize(baseResponse));
            res.getWriter().flush();
            return;
        }
        try {
            TLVarManager.setCurrentUser(new TLVarManager.User(userId, openId));
            MDC.put(Constants.SESSION_ATTR_USER_ID, userId.toString());
            MDC.put(Constants.SESSION_ATTR_OPEN_ID, openId);
            chain.doFilter(req, res);
        } catch (Throwable t) {
            if (t instanceof BizException) {
                baseResponse.setStatus(buildRespStatus(((BizException) t).getErrorCode()));
            } else {
                LOG.error(t.getMessage(), t);
                baseResponse.setStatus(new ResponseStatus(String.valueOf(ErrorCode.SYSTEM_ERROR), t.getMessage()));
            }
            res.getWriter().write(jsonMapper.serialize(baseResponse));
            res.getWriter().flush();
        } finally {
            TLVarManager.removeCurrentUser();
            MDC.remove(Constants.SESSION_ATTR_USER_ID);
            MDC.remove(Constants.SESSION_ATTR_OPEN_ID);
        }

    }

}
