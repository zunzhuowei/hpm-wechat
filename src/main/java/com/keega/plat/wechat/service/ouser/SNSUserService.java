package com.keega.plat.wechat.service.ouser;

import com.keega.plat.wechat.oauth2.pojo.SNSUserInfo;
import com.keega.plat.wechat.oauth2.pojo.WeixinOauth2Token;
import com.keega.plat.wechat.oauth2.util.AdvancedUtil;
import com.keega.plat.wechat.util.WeiXinConstant;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取授权后的用户信息业务层实现类
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class SNSUserService implements ISNSUserService {

    @Override//获取网页授权人的信息
    public Map<String,Object> getUserInfo(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        // 用户同意授权后，能获取到code
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        SNSUserInfo snsUserInfo = null;
        Map<String, Object> map = new HashMap<String, Object>();
        // 用户同意授权
        if (!"authdeny".equals(code) && null != state) {
            // 获取网页授权access_token
            WeixinOauth2Token weixinOauth2Token = AdvancedUtil.getOauth2AccessToken
                    (WeiXinConstant.APP_ID, WeiXinConstant.APP_SECRET, code);
            // 网页授权接口访问凭证
            String accessToken = weixinOauth2Token.getAccessToken();
            // 用户标识
            String openId = weixinOauth2Token.getOpenId();
            // 获取用户信息
            snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
            // 设置要传递的参数
            //request.setAttribute("snsUserInfo", snsUserInfo);
            //request.setAttribute("state", state);
            map.put("snsUserInfo", snsUserInfo);
            map.put("state", state);
        }
        return map;
    }


}
