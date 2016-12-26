package com.keega.plat.wechat.model.req;

/**
 * Created by zun.wei on 2016/12/7.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class ImageMessage extends RequestBaseMessage {

    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
