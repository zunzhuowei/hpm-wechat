package com.keega.plat.wecp.commons.wx.handler;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 测试授权操作
 *
 * Created by zun.wei on 2016/12/26.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Deprecated
@Component
public class Oauth2handler extends AbstractHandler {

    @Resource
    private WxCpConfigStorage msgConfigStorage;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map,
                                    WxCpService wxCpService, WxSessionManager wxSessionManager)
            throws WxErrorException {

        String href = "<a href=\""
                + wxCpService.oauth2buildAuthorizationUrl(
                msgConfigStorage.getOauth2redirectUri(), "state")
                + "\">测试oauth2</a>";
        return WxCpXmlOutMessage
                .TEXT()
                .content(href)
                .fromUser(wxCpXmlMessage.getToUserName())
                .toUser(wxCpXmlMessage.getFromUserName())
                .build();
    }

}
