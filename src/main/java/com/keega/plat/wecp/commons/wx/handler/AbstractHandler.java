package com.keega.plat.wecp.commons.wx.handler;

import com.google.gson.Gson;
import me.chanjar.weixin.cp.api.WxCpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息处理Handler的父类
 *
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public abstract class AbstractHandler implements WxCpMessageHandler {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected final Gson gson = new Gson();

}
