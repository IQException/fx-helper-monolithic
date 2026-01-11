package com.iqexception.fxhelper.util;

import com.google.common.hash.Hashing;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

public class HttpUtil {

    private static final char SEPARATOR = '_';

    public static String getToken(String userId, String sessionId) {
        if (StringUtils.isBlank(userId))
            return null;
        return Hashing.crc32().hashString(userId + sessionId, StandardCharsets.UTF_8).toString()
                + SEPARATOR + userId;
    }

    public static String getToken(Long userId, String sessionId) {
        return getToken(String.valueOf(userId), sessionId);
    }


    public static String getUserId(String token) {

        if (StringUtils.isBlank(token))
            return null;
        return token.substring(token.lastIndexOf(SEPARATOR) + 1);
    }

    public static boolean validateToken(String token, String sessionId) {

        if (StringUtils.isBlank(token))
            return false;
        String userId = token.substring(token.lastIndexOf(SEPARATOR) + 1);
        if (StringUtils.isBlank(userId))
            return false;
        return token.equals(getToken(userId, sessionId));
    }

    public static String wrapParam(String param) {
        return "{" + param + "}";
    }

    public static String getUrl(String url, String... params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        for (String param : params) {
            builder.queryParam(param, wrapParam(param));
        }
        return builder.encode().toUriString();
    }
}
