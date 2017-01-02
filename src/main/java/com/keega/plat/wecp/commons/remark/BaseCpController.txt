package com.keega.plat.wecp.controller.cp;

import com.keega.plat.wecp.commons.wx.aes.AesException;
import com.keega.plat.wecp.commons.wx.aes.WXBizMsgCrypt;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.*;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 本来使用来做基本的企业控制器父类的。
 * 现在已不使用，留着参考。
 *
 * Created by zun.wei on 2016/12/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Deprecated
public abstract class BaseCpController {

    //1.这个是主页型的应用所用---主页型
    private static WxCpInMemoryConfigStorage wxCpConfigStorage;
    private static WxCpService wxCpService;
    private static WxCpMessageRouter wxCpMessageRouter;

    //2.这个是消息型的应用所用---消息型
    private static WxCpInMemoryConfigStorage wxCpConfigStorageMsg;
    private static WxCpService wxCpServiceMsg;
    private static WxCpMessageRouter wxCpMessageRouterMsg;

    //初始化加载企业主页型应用参数---主页型
    static {
        wxCpConfigStorage = new WxCpInMemoryConfigStorage();
        wxCpConfigStorage.setCorpId("wx3a7775046bcc3267");      // 设置微信企业号的appid
        wxCpConfigStorage.setCorpSecret("EZU1SIcOo17WvE-W3CbRsIuVAHP_No9XKP4rOJt49zMVQUWFpFqmnkYJznNMi4YB");  // 设置微信企业号的app corpSecret
        wxCpConfigStorage.setAgentId(4);     // 设置微信企业号应用ID
        wxCpConfigStorage.setToken("weizhuozun");       // 设置微信企业号应用的token
        wxCpConfigStorage.setAesKey("iiUWeAGHFWbLWo9Gy9WkIBE22JJeinPxuKg1JDx3aoW");      // 设置微信企业号应用的EncodingAESKey

        wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(wxCpConfigStorage);

        WxCpMessageHandler handler = new WxCpMessageHandler() {
            @Override
            public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map,
                                            WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
                WxCpXmlOutTextMessage m = WxCpXmlOutMessage
                        .TEXT()
                        .content("测试加密消息")
                        .fromUser(wxCpXmlMessage.getToUserName())
                        .toUser(wxCpXmlMessage.getFromUserName())
                        .build();
                return m;
            }
        };

        wxCpMessageRouter = new WxCpMessageRouter(wxCpService);
        wxCpMessageRouter
                .rule()
                .async(false)
                .content("哈哈") // 拦截内容为“哈哈”的消息
                .handler(handler)
                .end();
    }

    //初始化加载企业消息型应用参数---消息型
    static {
        wxCpConfigStorageMsg = new WxCpInMemoryConfigStorage();
        wxCpConfigStorageMsg.setCorpId("wx3a7775046bcc3267");      // 设置微信企业号的appid
        wxCpConfigStorageMsg.setCorpSecret("EZU1SIcOo17WvE-W3CbRsIuVAHP_No9XKP4rOJt49zMVQUWFpFqmnkYJznNMi4YB");  // 设置微信企业号的app corpSecret
        wxCpConfigStorageMsg.setAgentId(5);     // 设置微信企业号应用ID
        wxCpConfigStorageMsg.setToken("weizhuozun");       // 设置微信企业号应用的token
        wxCpConfigStorageMsg.setAesKey("lj9PTif4BKTR15vQccrefqX8G3eNKawzYLa8yJeN7CQ");      // 设置微信企业号应用的EncodingAESKey

        wxCpServiceMsg = new WxCpServiceImpl();
        wxCpServiceMsg.setWxCpConfigStorage(wxCpConfigStorageMsg);

        WxCpMessageHandler handler = new WxCpMessageHandler() {
            @Override
            public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map,
                                            WxCpService wxCpService, WxSessionManager wxSessionManager) throws WxErrorException {
                WxCpXmlOutTextMessage m = WxCpXmlOutMessage
                        .TEXT()
                        .content("测试加密消息")
                        .fromUser(wxCpXmlMessage.getToUserName())
                        .toUser(wxCpXmlMessage.getFromUserName())
                        .build();
                return m;
            }
        };

        wxCpMessageRouterMsg = new WxCpMessageRouter(wxCpServiceMsg);
        wxCpMessageRouterMsg
                .rule()
                .async(false)
                .content("哈哈") // 拦截内容为“哈哈”的消息
                .handler(handler)
                .end();
    }

    /**
     * 申请回调模式验证初始化
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param type null表示验证的是主页型的应用，不为空表示验证的是消息型
     * @throws IOException 异常
     * @throws AesException 异常
     */
    protected void initCp(HttpServletRequest request, HttpServletResponse response,String type) throws IOException, AesException {

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String msgSignature = request.getParameter("msg_signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        String echostr = request.getParameter("echostr");

        if (null != type) {//如果不为空表示验证的是消息型应用
            wxCpService = wxCpServiceMsg;
            wxCpMessageRouter = wxCpMessageRouterMsg;
            wxCpConfigStorage = wxCpConfigStorageMsg;
        }

        if (StringUtils.isNotBlank(echostr)) {
            if (!wxCpService.checkSignature(msgSignature, timestamp, nonce, echostr)) {
                // 消息签名不正确，说明不是公众平台发过来的消息
                response.getWriter().println("非法请求");
                return;
            }
            WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
            //String plainText = cryptUtil.decrypt(echostr);
            String plainText = new WXBizMsgCrypt(wxCpConfigStorage.getToken(),
                    wxCpConfigStorage.getAesKey(),wxCpConfigStorage.getCorpId()).decrypt(echostr);
            // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(plainText);
            return;
        }

        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(request.getInputStream(),
                wxCpConfigStorage, timestamp, nonce, msgSignature);
        WxCpXmlOutMessage outMessage = wxCpMessageRouter.route(inMessage);
        System.out.println(outMessage.toString()+"<--------------------");
        if (outMessage != null) {
            response.getWriter().write(outMessage.toEncryptedXml(wxCpConfigStorage));
        }
        return;

    }

}
