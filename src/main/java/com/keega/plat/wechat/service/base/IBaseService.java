package com.keega.plat.wechat.service.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

/**
 * 基本的服务层接口
 * Created by zun.wei on 2016/12/8.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IBaseService {

    public static final String APPLICATION_XML_UTF8 = "application/xml;charset=UTF-8";
    public static final String UTF8 = "UTF-8";

    /**
     * 发送文本消息
     *
     * @param response     HttpServletResponse
     * @param xmlStringMsg xml格式的字符串消息
     * @throws IOException 异常
     */
    public void sendXmlMsg(HttpServletResponse response, String xmlStringMsg)
            throws IOException;

    /**
     * 检查校验与微信的链接
     * @param request
     * @param response
     * @throws IOException
     */
    public void checkWeixinConnection(HttpServletRequest request, HttpServletResponse response)
            throws IOException;

    /**
     * 制作菜单
     * @return 返回值是制作的菜单成功与否
     */
    public String makeMenu() throws KeyManagementException, NoSuchAlgorithmException, IOException;

    /**
     * 请求的xml字符串转换成Map----解析微信发来的请求（XML）
     * @param request HttpServletRequest
     * @return HashMap
     * @throws Exception 异常
     */
    public Map<String, String> requestMsg2Map(HttpServletRequest request) throws Exception ;

    //根据请求返回的xml解析之后获得的map，基于微信相应的返回值。

    /**
     *操作消息
     * @param msgMap HashMap
     * @return 给予移动端相应的回复
     */
    public String handleMsgType(Map<String, String> msgMap,HttpSession session) throws SQLException;
}
