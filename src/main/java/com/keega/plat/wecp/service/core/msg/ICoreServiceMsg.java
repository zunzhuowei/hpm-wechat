package com.keega.plat.wecp.service.core.msg;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.List;

/**
 * 核心业务接口
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ICoreServiceMsg {

    /**
     * HttpGet请求
     *
     * @param urlWithParams
     * @throws Exception
     */
    void requestGet(String urlWithParams) throws IOException;

    /**
     * HttpPost请求
     *
     * @param url
     * @param params
     * @throws ClientProtocolException
     * @throws IOException
     */
    void requestPost(String url, List<NameValuePair> params) throws ClientProtocolException, IOException;

    /**
     * 刷新消息路由器
     */
    void refreshRouter();

    /**
     * 路由消息
     *
     * @param inMessage
     * @return
     */
    WxCpXmlOutMessage route(WxCpXmlMessage inMessage);

    /**
     * 通过userid获得基本用户信息
     *
     * @param userid 企业用户用户id
     * @return 微信企业用户
     */
    WxCpUser getUserInfo(String userid) throws WxErrorException;

}
