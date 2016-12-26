package com.keega.plat.wecp.commons.wx.handler;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutNewsMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 事件操作
 * Created by zun.wei on 2016/12/25.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class EvenHandler extends AbstractHandler {

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map,
                                    WxCpService wxCpService, WxSessionManager wxSessionManager)
            throws WxErrorException {

        String event = wxCpXmlMessage.getEvent();
        String eventKey = wxCpXmlMessage.getEventKey();

        WxCpXmlOutNewsMessage.Item  item = new WxCpXmlOutNewsMessage.Item();
        item.setTitle("测试标题");
        item.setDescription("测试描述");
        item.setUrl("www.baidu.com");
        item.setPicUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1482681297&di=26c53323acd7251304107282a07e4acd&src=http://g.hiphotos.baidu.com/image/pic/item/f3d3572c11dfa9ecfc13ccc066d0f703918fc12c.jpg");

        /*if (event.equals("enter_agent") || event.equals("LOCATION"))
        return WxCpXmlOutTextMessage.NEWS().fromUser(wxCpXmlMessage.getToUserName())
                .toUser(wxCpXmlMessage.getFromUserName()).addArticle(item)
                .build();*/


        if ("click".equals(event) && "salary".equals(eventKey)) {//点击查询我的薪资
            return WxCpXmlOutTextMessage.TEXT()
                    .content("点击了我的薪资！")
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .build();
        }
        if ("click".equals(event) && "help".equals(eventKey)) {//点击帮助
            return WxCpXmlOutTextMessage.TEXT()
                    .content("点击了帮助！")
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .build();
        }

        if (event.equals("LOCATION")) {//如果不是位置上传
            return WxCpXmlOutTextMessage.TEXT()
                    .content("")
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .build();
        }

        return WxCpXmlOutTextMessage.TEXT()
                .content("你触发的事件是"+event+";你触发的的事件的key是:"+eventKey)
                .fromUser(wxCpXmlMessage.getToUserName())
                .toUser(wxCpXmlMessage.getFromUserName())
                .build();
    }

}
