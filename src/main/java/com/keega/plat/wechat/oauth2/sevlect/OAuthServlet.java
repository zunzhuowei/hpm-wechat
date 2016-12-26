package com.keega.plat.wechat.oauth2.sevlect;

import com.keega.plat.wechat.oauth2.pojo.SNSUserInfo;
import com.keega.plat.wechat.oauth2.pojo.WeixinOauth2Token;
import com.keega.plat.wechat.oauth2.pojo.WeixinUserInfo;
import com.keega.plat.wechat.oauth2.util.AdvancedUtil;
import com.keega.plat.wechat.util.WeiXinConstant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权的servlet，这里注释掉Annotation了，
 * 因为现在这个暂时用不上
 *
 * Created by zun.wei on 2016/12/14.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
//@WebServlet(name = "OAuthServlet",urlPatterns = {"/*"})
public class OAuthServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        // 用户同意授权
        if (!"authdeny".equals(code)) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken
                    (WeiXinConstant.APP_ID, WeiXinConstant.APP_SECRET, code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);

            // 设置要传递的参数
            request.setAttribute("snsUserInfo", snsUserInfo);
            request.setAttribute("state", state);
        }
        // 跳转到index.jsp
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
