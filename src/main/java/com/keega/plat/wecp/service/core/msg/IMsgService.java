package com.keega.plat.wecp.service.core.msg;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.IOException;

/**
 * 消息类型业务层接口
 * Created by zun.wei on 2016/12/27.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IMsgService {

    /**
     * 发送文本消息给企业用户
     */
    void sendTextMsg2User() throws WxErrorException;

    /**
     * 发送图文消息给企业用户
     */
    void sendImgTextMsg2User() throws WxErrorException;

    /**
     *
     * @return 上传媒体文件返回的结果对象。
     */
    WxMediaUploadResult uploadMedia() throws WxErrorException, IOException;

    /**
     * 发送图片消息给用户
     * @throws WxErrorException 异常
     */
    void sendImgMsg2User() throws WxErrorException;

}
