package com.iqexception.fxhelper.util;

import com.apifan.common.random.RandomSource;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class FakeDataUtil {
    private static final String GRAVATAR_BASE_URL = "https://cn.cravatar.com/avatar";

    public static String getRandomGravatarUrl() {

        String hash = Hashing.md5().hashBytes(
                RandomSource.internetSource().randomEmail(20).getBytes(StandardCharsets.UTF_8))
                .toString();

        return GRAVATAR_BASE_URL + "/" + hash + "?d=identicon";

    }

    public static String getRandomNickname(){
        return RandomSource.personInfoSource().randomChineseNickName(6);
    }

}
