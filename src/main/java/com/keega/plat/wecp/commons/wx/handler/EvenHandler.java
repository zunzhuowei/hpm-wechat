package com.keega.plat.wecp.commons.wx.handler;

import com.keega.plat.wecp.service.sys.ICpSysUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpXmlMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutNewsMessage;
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Map;

/**
 * 事件操作
 * Created by zun.wei on 2016/12/25.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class EvenHandler extends AbstractHandler {

    @Resource
    private ICpSysUserService cpSysUserService;

    @Override
    public WxCpXmlOutMessage handle(WxCpXmlMessage wxCpXmlMessage, Map<String, Object> map,
                                    WxCpService wxCpService, WxSessionManager wxSessionManager)
            throws WxErrorException {

        String event = wxCpXmlMessage.getEvent();
        String eventKey = wxCpXmlMessage.getEventKey();

        WxCpXmlOutNewsMessage.Item  item = new WxCpXmlOutNewsMessage.Item();
        item.setTitle("欢迎来到keega软件");
        item.setDescription("welcome to keega soft !");
        item.setUrl("http://161818x71d.iask.in/cp/msg/info");
        item.setPicUrl("http://ds.devstore.cn/20151125/1448419922203/QQ%BD%D8%CD%BC20151125104441.png");

        /*if ("click".equals(event)) {
            return WxCpXmlOutTextMessage.NEWS().fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName()).addArticle(item)
                    .build();
        }*/

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

        if (event.equals("LOCATION") || event.equals("enter_agent")) {//如果不是位置上传
            return WxCpXmlOutTextMessage.TEXT()
                    .content("")
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .build();
        }

        if (event.equals("view")) {//view事件不作处理
            return WxCpXmlOutTextMessage.TEXT()
                    .content("")
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .build();
        }

        if (event.equals("unsubscribe")){//取消关注的时候应该对该用户进行清空hr账号的绑定。
            try {
                System.out.println("用户:"+wxCpXmlMessage.getFromUserName()+"取消了企业号的订阅!");
                cpSysUserService.resetWeixinUserWithHr(wxCpXmlMessage.getFromUserName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (event.equals("subscribe")) {//关注企业号，并且认证成功之后。
            /*return WxCpXmlOutTextMessage.TEXT()
                    .content("恭喜您已经成功关注keega软件企业号！")
                    .toUser(wxCpXmlMessage.getFromUserName())
                    .fromUser(wxCpXmlMessage.getToUserName())
                    .build();*/
            return WxCpXmlOutTextMessage.NEWS().fromUser(wxCpXmlMessage.getToUserName())
                    .toUser(wxCpXmlMessage.getFromUserName()).addArticle(item)
                    .build();
        }

        return WxCpXmlOutTextMessage.TEXT()
                .content("你触发的事件是"+event+";你触发的的事件的key是:"+eventKey)
                .fromUser(wxCpXmlMessage.getToUserName())
                .toUser(wxCpXmlMessage.getFromUserName())
                .build();
    }

}
