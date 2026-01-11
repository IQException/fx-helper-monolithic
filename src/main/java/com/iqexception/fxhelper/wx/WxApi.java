package com.iqexception.fxhelper.wx;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.iqexception.fxhelper.support.Constants;
import com.iqexception.fxhelper.support.JsonMapper;
import com.iqexception.fxhelper.util.BizException;
import com.iqexception.fxhelper.util.ErrorCode;
import com.iqexception.fxhelper.util.HttpUtil;
import com.iqexception.fxhelper.wx.model.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.iqexception.fxhelper.wx.WxConstants.*;

@Component
public class WxApi {

    private static final Logger LOG = LoggerFactory.getLogger(WxApi.class);
    private volatile String ACCESS_TOKEN = null;

    private final RestTemplate restTemplate;

    private final JsonMapper jsonMapper;

    private final WxConfig wxConfig;

    public WxApi(RestTemplate restTemplate, JsonMapper jsonMapper ,WxConfig wxConfig) {
        this.restTemplate = restTemplate;
        this.jsonMapper = jsonMapper;
        this.wxConfig = wxConfig;
    }

    public Code2SessionResponse code2Session(String code) {

        String url = HttpUtil.getUrl("https://api.weixin.qq.com/sns/jscode2session",
                APP_ID_KEY, APP_SECRET_KEY, "js_code", GRANT_TYPE_KEY);

        Map<String, String> params = ImmutableMap.of(APP_ID_KEY, wxConfig.getAppId(),
                APP_SECRET_KEY, wxConfig.getAppSecret(),
                "js_code", code,
                GRANT_TYPE_KEY, "authorization_code");

        return restTemplate.getForObject(url, Code2SessionResponse.class, params);

    }

    @PostConstruct
    public void getAccessTokenJob() {
        int delay = 30;

        try {
            GetAccessTokenResponse response = doGetAccessToken();
            if (StringUtils.isNotBlank(response.getAccessToken())) {
                ACCESS_TOKEN = response.getAccessToken();
                delay = response.getExpiresIn() > 300 ? 300 : response.getExpiresIn();
            }
            LOG.info("access token:{},delay:{}", jsonMapper.serialize(response), delay);
        } finally {
            Executors.newScheduledThreadPool(1).schedule(this::getAccessTokenJob, delay, TimeUnit.SECONDS);
        }
    }

    private GetAccessTokenResponse doGetAccessToken() {

        LOG.info("doGetAccessToken: " + LocalDateTime.now().format(Constants.BASIC_FORMATTER));

        String url = HttpUtil.getUrl("https://api.weixin.qq.com/cgi-bin/token",
                APP_ID_KEY, APP_SECRET_KEY, GRANT_TYPE_KEY);

        Map<String, String> params = ImmutableMap
                .of(APP_ID_KEY, wxConfig.getAppId(),
                        APP_SECRET_KEY, wxConfig.getAppSecret(),
                        GRANT_TYPE_KEY, "client_credential");

        return restTemplate.getForObject(url, GetAccessTokenResponse.class, params);
    }

    public CommonResponse sendMessage(String templateId,
                                      String openId,
                                      String page,
                                      Object data) {


        SendMessageRequest request = new SendMessageRequest();
        request.setData(data);
        request.setPage(page);
        request.setTemplateId(templateId);
        request.setToUser(openId);
        //FIXME developer/trial/formal
        request.setMpState("trial");
        request.setLang("zh_CN");

        return restTemplate.postForObject(
                "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + ACCESS_TOKEN,
                request, CommonResponse.class);
    }

    public List<String> getQrCodes(String page, List<String> scenes) {

        List<String> qrCodes = new ArrayList<>();

        scenes.forEach(scene -> {
            GetQRCodeRequest request = new GetQRCodeRequest(page, scene);
            request.setCheckPath(false);
            //FIXME develop/trial/release
            request.setEnvVersion("trial");
            ResponseEntity<byte[]> response = restTemplate.postForEntity(
                    "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + ACCESS_TOKEN,
                    request, byte[].class);

            byte[] buffer = response.getBody();
            String contentType = Joiner.on(" ").join(Objects.requireNonNull(response.getHeaders().get("Content-Type")));
            if (contentType.contains("image/")) {
                qrCodes.add(new String(Base64.getEncoder().encode(buffer), StandardCharsets.UTF_8));
            } else {
                LOG.error("getQrCodes failed! response:{}", buffer == null ? "" : new String(buffer, StandardCharsets.UTF_8));
                throw new BizException(ErrorCode.PARAM_ERROR);
            }

        });

        return qrCodes;

    }
}
