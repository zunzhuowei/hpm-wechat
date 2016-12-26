package com.keega.plat.wechat.model.resp;

/**
 * Created by zun.wei on 2016/12/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class ImageMessage extends ResponseBaseMessage {

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image Image) {
        this.Image = Image;
    }
}
