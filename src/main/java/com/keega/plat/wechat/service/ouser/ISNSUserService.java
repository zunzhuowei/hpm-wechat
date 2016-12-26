package com.keega.plat.wechat.service.ouser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 获取授权后的用户信息业务层接口
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface ISNSUserService {

    /**
     * 获取网页授权人的信息
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return 包含着SNSUserInfo和sate的hashMap
     * @throws UnsupportedEncodingException 异常
     */
    public Map<String,Object> getUserInfo(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException;

}
