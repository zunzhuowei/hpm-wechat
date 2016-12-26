package com.keega.plat.wecp.controller.cp;

import com.google.gson.Gson;
import com.keega.plat.wecp.service.core.home.ICoreServiceHome;
import com.keega.plat.wecp.service.core.msg.ICoreServiceMsg;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信顶级控制器，用作企业控制器的父类
 *
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public abstract class CpController {

    @Resource
    private WxCpConfigStorage homeConfigStorage;
    @Resource
    private WxCpService wxHomeCpService;
    @Resource
    private WxCpConfigStorage msgConfigStorage;
    @Resource
    private WxCpService wxMsgCpService;
    @Resource
    private ICoreServiceHome coreServiceHome;
    @Resource
    private ICoreServiceMsg coreServiceMsg;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    //企业号主页型应用初始化
    protected void initCpHome(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String msgSignature = request.getParameter("msg_signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        String echostr = request.getParameter("echostr");

        if (isEcho(msgSignature, nonce, timestamp, echostr,
                response,wxHomeCpService,homeConfigStorage)) {
            return;
        }
        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(),
                homeConfigStorage, timestamp, nonce, msgSignature);
        WxCpXmlOutMessage outMessage = this.coreServiceHome.route(inMessage);//消息路由
        if (outMessage != null) {
            response.getWriter().write(outMessage.toEncryptedXml(homeConfigStorage));
        }
        response.getWriter().println("不可识别的加密类型");
        return;
    }

    //企业号消息型应用初始化
    protected void initCpMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String msgSignature = request.getParameter("msg_signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        String echostr = request.getParameter("echostr");

        if (isEcho(msgSignature, nonce, timestamp, echostr,
                response,wxMsgCpService,msgConfigStorage)) {
            return;
        }
        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(),
                msgConfigStorage, timestamp, nonce, msgSignature);
        WxCpXmlOutMessage outMessage = this.coreServiceMsg.route(inMessage);//消息路由
        if (outMessage != null) {
            response.getWriter().write(outMessage.toEncryptedXml(msgConfigStorage));
        }
        response.getWriter().println("不可识别的加密类型");
        return;
    }

    //是否为回显消息
    private boolean isEcho(String msgSignature,String nonce,String timestamp,String echostr
            ,HttpServletResponse response,WxCpService wxCpService,WxCpConfigStorage wxCpConfigStorage) throws IOException {
        if (StringUtils.isNotBlank(echostr)) {
            if (!wxCpService.checkSignature(msgSignature, timestamp, nonce, echostr)) {
                // 消息签名不正确，说明不是公众平台发过来的消息
                response.getWriter().println("非法请求");
                return true;
            }
            WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
            String plainText = cryptUtil.decrypt(echostr);
            // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(plainText);
            return true;
        }
        return false;
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response HttpServletResponse
     * @param object 要返回的转换成json的对象
     * @return json字符串
     */
    protected String renderString(HttpServletResponse response, Object object) {
        return renderString(response, new Gson().toJson(object), "application/json");
    }

    /**
     * 客户端返回字符串
     *
     * @param response HttpServletResponse
     * @param string 要返回的字符串
     * @return json字符串
     */
    protected String renderString(HttpServletResponse response, String string, String type) {
        try {
            response.reset();
            response.setContentType(type);
            response.setCharacterEncoding("utf-8");
            //解决跨域问题
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.getWriter().print(string);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    //消息型微信回调请求
    //@RequestMapping(value = "/msg/init", method = RequestMethod.POST)
    /*public void wxCallBackInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        final String msgSignature = request.getParameter("msg_signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        String echostr = request.getParameter("echostr");

        WxCpMessageHandler handler = new WxCpMessageHandler() {
            @Override
            public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
                                            Map<String, Object> context, WxCpService wxService,
                                            WxSessionManager sessionManager) {
                WxCpXmlOutTextMessage m = WxCpXmlOutMessage.TEXT().content("测试加密消息")
                        .fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName()).build();
                return m;
            }
        };

        WxCpMessageHandler oauth2handler = new WxCpMessageHandler() {
            @Override
            public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
                                            Map<String, Object> context, WxCpService wxService,
                                            WxSessionManager sessionManager) {
                String href = "<a href=\""
                        + wxService.oauth2buildAuthorizationUrl(
                        msgConfigStorage.getOauth2redirectUri(), "state")
                        + "\">测试oauth2</a>";
                return WxCpXmlOutMessage.TEXT().content(href)
                        .fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName()).build();
            }
        };

        wxCpMessageRouter = new WxCpMessageRouter(wxMsgCpService);
        wxCpMessageRouter.rule().async(false).content("哈哈") // 拦截内容为“哈哈”的消息
                .handler(handler).end().rule().async(false).content("oauth")
                .handler(oauth2handler).end();

        WxCpXmlMessage inMessage = WxCpXmlMessage
                .fromEncryptedXml(request.getInputStream(), this.msgConfigStorage, timestamp, nonce, msgSignature);
        WxCpXmlOutMessage outMessage = wxCpMessageRouter.route(inMessage);
System.out.println(inMessage);
        if (outMessage != null) {
            response.getWriter().write(outMessage.toEncryptedXml(this.msgConfigStorage));
        }

        return;

        //System.out.println(request.getContextPath()+"msg/init");
    }*/

}
