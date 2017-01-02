package com.keega.plat.wecp.service.core.msg.impl;

import com.keega.plat.wecp.commons.wx.handler.*;
import com.keega.plat.wecp.service.core.msg.ICoreServiceMsg;
import com.keega.plat.wecp.util.http.HttpRequestUtil;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 核心业务层
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class CoreServiceMsg implements ICoreServiceMsg {

    @Resource(name = "wxMsgCpService")
    private WxCpService wxMsgCpService;
    @Resource
    private LogHandler logHandler;
    @Resource
    protected MsgHandler msgHandler;
    @Resource
    private EvenHandler evenHandler;
    @Resource
    private Oauth2handler oauth2handler;

    @Resource
    private HttpRequestUtil httpRequestUtil;

    private Logger logger = LoggerFactory.getLogger(getClass());
    private WxCpMessageRouter router;

    @PostConstruct
    public void init() {
        this.refreshRouter();
    }

    @Override
    public void requestGet(String urlWithParams) throws IOException {
       httpRequestUtil.get(urlWithParams);
    }

    @Override
    public void requestPost(String url, List<NameValuePair> params) throws IOException {
       httpRequestUtil.post(url,params);
    }

    /**
     *
     wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
     wxCpMessageRouter.rule().async(false).content("哈哈") // 拦截内容为“哈哈”的消息
     .handler(handler).end().rule().async(false).content("oauth")
     .handler(oauth2handler).end();
     *
     */
    @Override
    public void refreshRouter() {
        final WxCpMessageRouter newRouter = new WxCpMessageRouter(
                this.wxMsgCpService);
        // 记录所有事件的日志
        newRouter.rule().handler(this.logHandler).next();
        // 关注事件
       /* newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.EVT_SUBSCRIBE).handler(this.subscribeHandler)
                .end();*/
        // 默认,转发消息给客服人员
        newRouter.rule().async(false).msgType("text").content("授权").handler(oauth2handler).end();
        newRouter.rule().async(false).msgType("text").handler(msgHandler).end();
        newRouter.rule().async(false).msgType("event").handler(evenHandler).end();
        this.router = newRouter;
    }

    @Override
    public WxCpXmlOutMessage route(WxCpXmlMessage inMessage) {
        try {
            return this.router.route(inMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public WxCpUser getUserInfo(String userid) throws WxErrorException {
        return this.wxMsgCpService.userGet(userid);
    }


}
