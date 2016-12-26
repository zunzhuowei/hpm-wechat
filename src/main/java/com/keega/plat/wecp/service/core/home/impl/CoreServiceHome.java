package com.keega.plat.wecp.service.core.home.impl;

import com.keega.plat.wecp.commons.wx.handler.LogHandler;
import com.keega.plat.wecp.commons.wx.handler.MsgHandler;
import com.keega.plat.wecp.service.core.home.ICoreServiceHome;
import com.keega.plat.wecp.util.http.HttpRequestUtil;
import me.chanjar.weixin.cp.api.WxCpMessageRouter;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 核心业务层
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class CoreServiceHome implements ICoreServiceHome {

    @Resource
    private WxCpService wxHomeCpService;

    @Resource
    private LogHandler logHandler;

    @Resource
    protected MsgHandler msgHandler;
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

    @Override
    public void refreshRouter() {
        final WxCpMessageRouter newRouter = new WxCpMessageRouter(
                this.wxHomeCpService);
        // 记录所有事件的日志
        newRouter.rule().handler(this.logHandler).next();
        // 关注事件
       /* newRouter.rule().async(false).msgType(WxConsts.XML_MSG_EVENT)
                .event(WxConsts.EVT_SUBSCRIBE).handler(this.subscribeHandler)
                .end();*/
        // 默认,转发消息给客服人员
        newRouter.rule().async(false).handler(this.msgHandler).end();
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

}
