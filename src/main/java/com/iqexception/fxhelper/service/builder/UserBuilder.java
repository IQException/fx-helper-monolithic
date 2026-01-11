package com.iqexception.fxhelper.service.builder;

import com.google.common.hash.Hashing;
import com.iqexception.fxhelper.dal.generator.tables.pojos.FxUser;
import com.iqexception.fxhelper.util.FakeDataUtil;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class UserBuilder {
    public static FxUser buildUserByFakeData(String openId) {
        FxUser user =new FxUser();
        user.setOpenId(openId);
        user.setAvatar(FakeDataUtil.getRandomGravatarUrl());
        user.setNickName(FakeDataUtil.getRandomNickname());
        return user;

    }
    public static FxUser buildUserByFakeData(FxUser user, String sessionKey) {

        user.setSessionKey(sessionKey);
        user.setAvatar(FakeDataUtil.getRandomGravatarUrl());
        user.setNickName(FakeDataUtil.getRandomNickname());
        user.setUpdatedAt(LocalDateTime.now());
        return user;

    }

    public static String encryptPaySecret(String paySecret) {

        if (StringUtils.isBlank(paySecret)) return null;

        return Hashing.sha256().hashString(paySecret, StandardCharsets.UTF_8).toString();

    }


}
