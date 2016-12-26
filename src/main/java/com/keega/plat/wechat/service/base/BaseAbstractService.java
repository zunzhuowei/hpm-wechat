package com.keega.plat.wechat.service.base;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * Created by zun.wei on 2016/12/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public abstract class BaseAbstractService {

    private static final String token = "weizhuozun";

    //校验签名
    boolean checkSignature(String signature, String timestamp, String nonce){
        String[] arr = new String[] { token, timestamp, nonce };
        // 排序
        Arrays.sort(arr);
        // 生成字符串
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        // sha1加密
        String temp = getSHA1String(content.toString());
        return temp.equals(signature); // 与微信传递过来的签名进行比较
    }

    //获取sha1加密之后的密匙
    private String getSHA1String(String data){
        return DigestUtils.sha1Hex(data);    // 使用commons codec生成sha1字符串
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////
    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";





    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";






    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 未关注时，扫描二维码的EvenKey
     */
    public static final String EVENT_KEY_Qrscene_ = "qrscene_";

    /**
     * 已关注时，扫描二维码的EvenKey
     */
    public static final String EVENT_KEY_SCENE_ = "SCENE_";

    /**
     * 事件类型：SCAN(二维码扫描事件)
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";

    /**
     * 事件类型：LOCATION（上报地理位置事件）
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";




}
