package com.keega.plat.wechat.service.msg;

import com.keega.plat.wechat.model.resp.Image;
import com.keega.plat.wechat.model.resp.ImageMessage;
import com.keega.plat.wechat.service.base.BaseService;
import com.keega.plat.wechat.util.xml.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by zun.wei on 2016/12/11.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class ImageTypeService extends BaseService {

    //图片类型操作
    public String imageTypeHandler(Map<String, String> msgMap) {
        try {
            ImageMessage imageMessage = new ImageMessage();
            imageMessage.setMsgType(REQ_MESSAGE_TYPE_IMAGE);
            imageMessage.setToUserName(msgMap.get("FromUserName"));
            imageMessage.setFromUserName(msgMap.get("ToUserName"));
            imageMessage.setCreateTime(new Date().getTime());
            Image image = new Image();
            image.setMediaId("crfMhg_Log6IZRRCMCoHdfzzepk_JQGTYWxHWc5Uuy1BKkGJtuUW3d2dldFNDuqi");
            imageMessage.setImage(image);
            //System.out.println(MessageUtil.imageMessgeToXml(imageMessage));
            return MessageUtil.imageMessgeToXml(imageMessage);
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
    }

}
