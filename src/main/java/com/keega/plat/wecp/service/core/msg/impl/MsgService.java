package com.keega.plat.wecp.service.core.msg.impl;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wecp.service.core.msg.IMsgService;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpMessage;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 消息类型业务层实现类
 * Created by zun.wei on 2016/12/27.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class MsgService implements IMsgService {

    @Resource
    private WxCpService wxMsgCpService;

    @Override
    public void sendTextMsg2User() throws WxErrorException {
        WxCpMessage sendAllUserSafe = WxCpMessage.TEXT().agentId(5)//.toParty("").toTag("")
                .content("又要开始丧心病狂啦！！！").toUser("@all")
                .safe("1").build();

        WxCpMessage sendSomeonesSafe = WxCpMessage.TEXT().agentId(5)//.toParty("").toTag("")
                .content("又要开始丧心病狂啦！！！").toUser("weizhuozun|wechatTester")
                .toParty("2|4").safe("1").build();

        //表示发送用户weizhuozun和部门id为4下的用户
        WxCpMessage sendOneSafe = WxCpMessage.TEXT().agentId(5)//.toParty("").toTag("")
                .content("测试消息发送").toUser("weizhuozun")
                .toParty("4").safe("1").build();

        //System.out.println("wxCpMessage = " + wxCpMessage.toJson());
        wxMsgCpService.messageSend(sendOneSafe);
    }

    @Override
    public void sendImgTextMsg2User() throws WxErrorException {
        WxCpMessage.WxArticle article1 = new WxCpMessage.WxArticle();
        article1.setUrl("www.baidu.com");
        article1.setPicUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?" +
                "image&quality=100&size=b4000_4000&sec=1482770756&di=e25da061f713b19c" +
                "bde8a156d628e88e&src=http://b.hiphotos.baidu.com/image/pic/item/0823dd54" +
                "564e925838c205c89982d158ccbf4e26.jpg");
        article1.setDescription("描述1");
        article1.setTitle("标题1");

        WxCpMessage.WxArticle article2 = new WxCpMessage.WxArticle();
        article2.setUrl("http://hr.ebchinaintl.com.cn/templates/index/hcmlogon.jsp");
        article2.setPicUrl("http://f.hiphotos.baidu.com/image/pic/item/d50735fae6cd7b899" +
                "b6bd2850d2442a7d9330eb4.jpg");
        article2.setDescription("描述2");
        article2.setTitle("标题2");

        WxCpMessage.WxArticle article3 = new WxCpMessage.WxArticle();
        article3.setUrl("www.jd.com");
        article3.setPicUrl("http://d.hiphotos.baidu.com/image/pic/item/0ff41bd5ad6eddc422" +
                "23e3f13bdbb6fd536633f7.jpg");
        article3.setDescription("描述3");
        article3.setTitle("标题3");

        WxCpMessage wxCpMessage = WxCpMessage.NEWS()
                .agentId(5) // 企业号应用ID
                .toUser("@all")
                //.toParty("非必填，PartyID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数")
                //.toTag("非必填，TagID列表，多个接受者用‘|’分隔。当touser为@all时忽略本参数")
                .addArticle(article1)
                .addArticle(article2)
                .addArticle(article3)
                .build();
        wxMsgCpService.messageSend(wxCpMessage);

    }

    //媒体文件类型，分别有图片（image）、语音（voice）、视频（video）
    // 和缩略图（thumb，主要用于视频与音乐格式的缩略图）
    @Override //String mediaType, String fileType, InputStream inputStream
    public WxMediaUploadResult uploadMedia() throws WxErrorException, IOException {
        File file = new File("F:\\firefox_download\\404.jpg");
        InputStream inputStream = FileUtils.openInputStream(file); //
        WxMediaUploadResult res = wxMsgCpService.mediaUpload("image", "jpg", inputStream);
        //res = wxCpService.mediaUpload(mediaType, file);方法二
System.out.println(JsonUtil.obj2json(res));
        return res;
    }

    @Override
    public void sendImgMsg2User() throws WxErrorException {
        //表示发送用户weizhuozun和部门id为4下的用户
        WxCpMessage sendOneSafe = WxCpMessage.IMAGE().agentId(5)
                .mediaId("1y0uhA0XLyM_ukaJ7Lnd7cepJCHWIyndzSTKv9_V850" +
                        "XMChsnT1E6LOI-zwv8LqYhNjlzlkdeE8Tojj_LAbkVXg")
                .toUser("weizhuozun").toTag("wechat")
                .toParty("4").safe("1").build();

        //System.out.println("wxCpMessage = " + wxCpMessage.toJson());
        wxMsgCpService.messageSend(sendOneSafe);
    }


}
