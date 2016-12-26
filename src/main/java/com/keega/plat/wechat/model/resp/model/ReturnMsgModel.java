package com.keega.plat.wechat.model.resp.model;

/**
 *
 * 返回消息模板
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class ReturnMsgModel {

    public static final String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
            "appid=wxdac78f942674b126" +
            "&redirect_uri=http%3A%2F%2F161818x71d.iask.in%2Fwx%2Factivation" +
            "&response_type=code&scope=snsapi_userinfo&state=STATE" +
            "&connect_redirect=1#wechat_redirect";

    public static final String WELCOME_MSG = "Hello,welcome to use keega soft！！";
}
