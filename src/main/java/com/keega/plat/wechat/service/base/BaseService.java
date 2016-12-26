package com.keega.plat.wechat.service.base;

import com.keega.plat.wechat.dao.base.BaseDao;
import com.keega.plat.wechat.dao.base.IBaseDao;
import com.keega.plat.wechat.service.msg.*;
import com.keega.plat.wechat.util.WeiXinConstant;
import com.keega.plat.wechat.util.http.HttpsPostClient;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基本服务层实现类
 * Created by zun.wei on 2016/12/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service("baseService")
public class BaseService extends BaseAbstractService implements IBaseService {

    @Resource
    private IBaseDao baseDao;
    @Resource
    private TextTypeService textTypeService;
    @Resource
    private LinkTypeService linkTypeService;
    @Resource
    private EvenTypeService evenTypeService;
    @Resource
    private LocationTypeService locationTypeService;
    @Resource
    private VoiceTypeService voiceTypeService;
    @Resource
    private ImageTypeService imageTypeService;

    @Override//发送xml消息
    public void sendXmlMsg(HttpServletResponse response, String xmlStringMsg) throws IOException {
        response.setContentType(APPLICATION_XML_UTF8);
        response.setCharacterEncoding(UTF8);
        response.getWriter().write(xmlStringMsg);
    }

    @Override//检查与微信链接
    public void checkWeixinConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 接收微信服务器以Get请求发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        if (checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);        // 校验通过，原样返回echostr参数内容
        }
        System.out.println("进来验证的controller");
        System.out.println("signature:"+signature + "---timestamp:"+timestamp
                + "----nonce:"+nonce +"---echostr:"+echostr);
    }

    @Override//制作菜单
    public String makeMenu() throws KeyManagementException, NoSuchAlgorithmException, IOException {
        //String accessToken = baseDao.getAccessToken();
        String accessToken = new BaseDao().getAccessToken();
        String url = WeiXinConstant.MENU_URL;
        url = url.replace("ACCESS_TOKEN", accessToken);
        return new HttpsPostClient().doPostByJson(url, new BaseDao().getMenuJson(), "utf-8");
        //return new HttpsPostClient().doPostByJson(url, baseDao.getMenuJson(), "utf-8");
    }

    @Override//解析微信发来的请求（XML）
    public Map<String, String> requestMsg2Map(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getTextTrim());
        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

    @Override//接受消息路由
    public String handleMsgType(Map<String, String> msgMap,HttpSession session) throws SQLException {
        String msgType = msgMap.get("MsgType");
        System.out.println("msgType = " + msgType);
        if (REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {//文本消息
            return textTypeService.textTypeHandler(msgMap);
        } else if (REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {//事件消息
            return evenTypeService.evenTypeHandler(msgMap,session);
        } else if (REQ_MESSAGE_TYPE_LINK.equals(msgType)) { //链接消息
            return linkTypeService.linkTypeHandler(msgMap);
        } else if (REQ_MESSAGE_TYPE_LOCATION.equals(msgType)) {//位置消息
            return locationTypeService.locationTypeHandler(msgMap);
        } else if (REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {//声音消息
            return voiceTypeService.voiceTypeHandler(msgType);
        } else if (REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {//图片消息处理
            return imageTypeService.imageTypeHandler(msgMap);
        }
        return "";
    }


}
