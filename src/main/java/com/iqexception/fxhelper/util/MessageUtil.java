package com.iqexception.fxhelper.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

/**
 * @author wang.wei
 * @since 2019/5/30
 */
public class MessageUtil {

    public static String message(int errorCode, MessageSource messageSource) {
        return message(String.valueOf(errorCode), messageSource);
    }

    /**
     * 翻译中文错误代码
     *
     * @param messageSource
     * @return
     */
    public static String message(String errorCode, MessageSource messageSource) {
        return message(errorCode, messageSource, (Object[]) null);
    }


    public static String message(int errorCode, MessageSource messageSource, Object... params) {
        return message(String.valueOf(errorCode), messageSource, params);
    }

    /**
     * 翻译中文错误代码
     *
     * @param messageSource
     * @return
     */
    public static String message(String errorCode, MessageSource messageSource, Object... params) {
        return message(errorCode, messageSource, Locale.CHINA, params);
    }

    /**
     * 翻译错误代码
     *
     * @param messageSource
     * @return
     */
    public static String message(String errorCode, MessageSource messageSource, Locale locale, Object... params) {
        if (messageSource == null) {
            return errorCode;
        }
        try {
            return messageSource.getMessage(errorCode, params, locale);
        } catch (NoSuchMessageException e) {
            return errorCode;
        }
    }

    /**
     * 尽可能找到异常堆栈中的中文信息,该方法的逻辑是从栈顶向下搜索中文消息,找到即返回.
     *
     * @param throwable
     * @param countLimit 递归查找的最大次数
     * @return
     */
    public static String findErrorMessage(Throwable throwable, int countLimit) {
        if (throwable == null)
            return null;
        if (countLimit == 0)
            return throwable.getMessage();
        if (countLimit < 0)
            throw new IllegalArgumentException("countLimit不能为负");
        if (throwable.getMessage() == null)
            return null;
        if (throwable.getCause() == null)
            return throwable.getMessage();
        if (chineseLength(throwable.getMessage()) != throwable.getMessage().length()) {
            return throwable.getMessage();
        } else {
            return findErrorMessage(throwable.getCause(), --countLimit);
        }
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int chineseLength(String value) {
        int valueLength = 0;
        if (StringUtils.isEmpty(value)) {
            return valueLength;
        }
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
//            String temp = value.substring(i, i + 1);
            String temp = String.valueOf(value.charAt(i));
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

}

