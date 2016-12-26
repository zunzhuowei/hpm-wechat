package com.keega.plat.wechat.exception;

/**
 *
 * 自定义微信异常
 * Created by zun.wei on 2016/12/13.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class WechatException extends RuntimeException {


    public WechatException() {
        super();
    }

    public WechatException(String message) {
        super(message);
    }

    public WechatException(String message, Throwable cause) {
        super(message, cause);
    }

    public WechatException(Throwable cause) {
        super(cause);
    }

    protected WechatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
