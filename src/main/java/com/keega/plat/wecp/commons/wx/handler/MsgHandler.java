package com.keega.plat.wecp.commons.wx.handler;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文字消息操作
 *
 * Created by zun.wei on 2016/12/24.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map,
                                    WxCpService wxCpService, WxSessionManager wxSessionManager)
            throws WxErrorException {

        System.out.println("wxCpXmlMessage.getContent = " + wxCpXmlMessage.getContent());
        String content = wxCpXmlMessage.getContent();
        //int agentId = wxCpXmlMessage.getAgentId();

        /*if (content.contains("测试"))
            return WxCpXmlOutTextMessage.VIDEO().toUser(wxCpXmlMessage.getFromUserName())
            .fromUser(wxCpXmlMessage.getToUserName()).description("视频描述").title("视频标题")
            .mediaId("mediaId").build();
         else if (content.contains("激活"))
             return WxCpXmlOutTextMessage.IMAGE().mediaId("mediaId").fromUser(wxCpXmlMessage.getToUserName())
             .toUser(wxCpXmlMessage.getFromUserName()).build();*/

        if (null != content && content.contains("激活"))
            return WxCpXmlOutTextMessage.TEXT().content("你是否要激活与hr系统绑定？")//回复的内容  测试加密消息
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .build();

        else if (null != content && content.contains("你好"))
            return WxCpXmlOutTextMessage.TEXT().content("welcome to keega soft development !\n" +
                    " how to use? \n please click the help button !")//回复的内容  测试加密消息
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .build();


        try {//测试session
            int inCount = Integer.parseInt(wxCpXmlMessage.getContent());
            WxSession wxSession = wxSessionManager.getSession(wxCpXmlMessage.getFromUserName());
            String returnContent = "";
            if (wxSession.getAttribute("count") == null){
                int initCount = RandomUtils.nextInt(0,100);
                wxSession.setAttribute("count", initCount);
                System.out.println("进来设置session,答案为："+initCount);
            }
            int count = (int) wxSession.getAttribute("count");
                //wxSession.removeAttribute("count");
                if (count>inCount)
                    returnContent = "你输入的数字小了！你继续猜！";
                if (count<inCount)
                    returnContent = "你输入的数字大了！你继续猜！";
                if (count == inCount){
                    returnContent = "恭喜你才对了！";
                    wxSession.removeAttribute("count");
                }
            return WxCpXmlOutTextMessage.TEXT()
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .content(returnContent)
                    .build();
        } catch (NumberFormatException e) {

            String c = "你说的:“"+content+"”;我不知道你什么意思！";
            if (content == null) c = "";
                return WxCpXmlOutTextMessage.TEXT()
                        .content(c)//回复的内容  测试加密消息
                        .fromUser(wxCpXmlMessage.getToUserName())
                        .toUser(wxCpXmlMessage.getFromUserName())
                        .build();
        }


    }

}
