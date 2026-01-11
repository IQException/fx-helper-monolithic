package com.iqexception.fxhelper.config;

import com.iqexception.fxhelper.service.UserService;
import com.iqexception.fxhelper.support.AccountSignVerifyFilter;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.support.PayVerifyFilter;
import com.iqexception.fxhelper.support.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterBeanConfig {
    @Bean
    public FilterRegistrationBean<SessionFilter> loginFilter(MessageSource messageSource, JsonMapper jsonMapper) {
        FilterRegistrationBean<SessionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionFilter(messageSource, jsonMapper));

        registrationBean.addUrlPatterns("/get_signed_upload_url");
        registrationBean.addUrlPatterns("/msg_subscribe");
        registrationBean.addUrlPatterns("/get_upload_policy");

        registrationBean.addUrlPatterns("/order/*");

        registrationBean.addUrlPatterns("/shop/*");

        registrationBean.addUrlPatterns("/pay/deposit");
        registrationBean.addUrlPatterns("/pay/withdraw");

        registrationBean.addUrlPatterns("/account/user_query");

        registrationBean.addUrlPatterns("/composite/*");


        registrationBean.setOrder(1);

        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean<PayVerifyFilter> payVerifyFilter(MessageSource messageSource, JsonMapper jsonMapper, UserService userService) {
        FilterRegistrationBean<PayVerifyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PayVerifyFilter(messageSource, jsonMapper, userService));
        // 提现需要校验支付密码
        registrationBean.addUrlPatterns("/pay/withdraw");
        // 在login filter后面
        registrationBean.setOrder(2);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AccountSignVerifyFilter> signVerifyFilter(MessageSource messageSource, JsonMapper jsonMapper) {
        FilterRegistrationBean<AccountSignVerifyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AccountSignVerifyFilter(messageSource, jsonMapper));

        // partner 需要验证签名
        registrationBean.addUrlPatterns("/account/partner_*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
