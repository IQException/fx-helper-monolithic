package com.iqexception.fxhelper.util;


/**
 * 通用错误码：成功：0; 客户端错误：100XX; 服务端错误：900XX；
 * 业务错误码：AA-BB-CC：  AA表示业务或项目(1-9预留给通用错误），10代表用户，20代表订单；
 * BB表示更细分的业务模块， 默认00 直接表示AA业务下的错误；
 * CC表示具体的错误。
 */
public class ErrorCode {

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 参数错误
     */
    public static final int PARAM_ERROR = 10001;

    /**
     * 签名错误
     */
    public static final int SIGN_ERROR = 10002;

    /**
     * 密码错误
     */
    public static final int PASSWORD_ERROR = 10003;

    /**
     * 系统异常
     */
    public static final int SYSTEM_ERROR = 90001;

    /**
     * 数据库异常
     */
    public static final int DB_ERROR = 90002;


    /**
     * 用户不存在
     */
    public static final int USER_NOT_EXIST = 100001;

    public static final int USER_NO_LOGIN = 100002;

    public static final int USER_SESSION_EXPIRED = 100003;

    public static final int USER_AUTH_REQUIRED = 100004;


    /**
     * 商家不存在
     */
    public static final int SHOP_NOT_EXIST = 200001;

    /**
     * 返现关闭
     */
    public static final int SHOP_CLOSED = 200002;


    public static final int ACCOUNT_FROM_NOT_EXIST = 300001;

    public static final int ACCOUNT_TO_NOT_EXIST = 300002;

    public static final int ACCOUNT_BALANCE_INSUFFICIENT = 300003;


    public static final int ORDER_NOT_EXIST = 400001;

    public static final int ORDER_DUPLICATE = 400002;


    public static final int MESSAGE_NOT_EXIST = 500001;

    public static final int MESSAGE_NOT_SUBSCRIBED = 500002;

}
