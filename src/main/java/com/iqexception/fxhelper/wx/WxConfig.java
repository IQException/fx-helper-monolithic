package com.iqexception.fxhelper.wx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:wx-config.properties")
@Configuration
public class WxConfig {

    @Value("${MP_APP_ID}")
    private String appId;

    @Value("${MP_APP_SECRET}")
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
