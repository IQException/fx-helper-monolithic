package com.iqexception.fxhelper.support;

import java.time.format.DateTimeFormatter;

public class Constants {

    public static final String BUCKET_NAME = "test-fx-helper";

    /**
     * yyyy-MM-dd HH:mm:SS
     */
    public static final DateTimeFormatter BASIC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final String SESSION_ATTR_USER_ID = "userId";

    public static final String SESSION_ATTR_OPEN_ID = "openId";

    public static final String HEADER_PAY_SECRET = "FX-PAY-SECRET";

    public static final String HEADER_ACCOUNT_SIGN = "FX-ACCOUNT-SIGN";




}
