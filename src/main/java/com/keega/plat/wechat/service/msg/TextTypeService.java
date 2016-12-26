package com.keega.plat.wechat.service.msg;

import com.keega.plat.wechat.model.resp.TextMessage;
import com.keega.plat.wechat.model.resp.model.ReturnMsgModel;
import com.keega.plat.wechat.service.base.BaseService;
import com.keega.plat.wechat.util.xml.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by zun.wei on 2016/12/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class TextTypeService extends BaseService{

    //文本类型操作
    public String textTypeHandler(Map<String, String> msgMap) {
        String content = msgMap.get("Content");
        System.out.println(content.indexOf("绑定"));
        if (content.contains("绑定"))//判断返回给用户绑定hr链接
            return sendContent(msgMap, ReturnMsgModel.AUTH_URL);
        try {
            return sendContent(msgMap, ReturnMsgModel.WELCOME_MSG);
        } catch (Exception e) {
            return "";
        }
    }

    //发送返回个用户的内容
    private String sendContent(Map<String, String> msgMap,String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);//TODO 返回消息msgMap.get("Content")
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setFromUserName(msgMap.get("ToUserName"));
        textMessage.setToUserName(msgMap.get("FromUserName"));
        textMessage.setMsgType(RESP_MESSAGE_TYPE_TEXT);
        //System.out.println(MessageUtil.textMessageToXml(textMessage));
        return MessageUtil.textMessageToXml(textMessage);
    }

}
