package com.keega.plat.wecp.commons.wx.handler;

import com.keega.plat.wecp.service.core.msg.ICoreServiceMsg;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 测试wxSession的使用
 *
 * Created by zun.wei on 2016/12/26.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Deprecated
@Component
public class WxSessionHandler extends AbstractHandler {

    /*@Resource
    private ICoreServiceMsg coreServiceMsg;*/

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map,
                                    WxCpService wxCpService, WxSessionManager wxSessionManager)
            throws WxErrorException {
        //WxCpUser wxCpUser = coreServiceMsg.getUserInfo(wxCpXmlMessage.getFromUserName(), "zh_CN");
        //WxCpUser wxCpUser = wxCpService.userGet();
        WxSession wxSession = wxSessionManager.getSession(wxCpXmlMessage.getFromUserName());
        String returnContent = "";
        if (wxSession.getAttribute("user") == null)
            wxSession.setAttribute("count", 10);
        int count = (int) wxSession.getAttribute("count");

        try {
            int inCount = Integer.parseInt(wxCpXmlMessage.getContent());
            if (count>inCount)
                returnContent = "你输入的数字小了！你继续猜！";
            if (count<inCount)
                returnContent = "你输入的数字大了！你继续猜！";
            if (count == inCount)
                returnContent = "恭喜你猜对了！";
        } catch (NumberFormatException e) {
            returnContent = "你输入的是不是数字！";
        }

            return WxCpXmlOutTextMessage.TEXT()
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .content(returnContent)
                    .build();
    }

}
