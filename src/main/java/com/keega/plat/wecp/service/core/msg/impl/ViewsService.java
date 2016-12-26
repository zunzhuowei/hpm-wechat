package com.keega.plat.wecp.service.core.msg.impl;

import com.keega.plat.wecp.service.core.msg.IViewsService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 视图业务层接口实现类
 *
 * Created by zun.wei on 2016/12/26.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Service
public class ViewsService implements IViewsService {

    @Resource
    private WxCpService wxMsgCpService;

    @Override//根据code获取授权用户的userID/OpenID
    public String getOauth2UserInfoIdByCode(String code) throws WxErrorException {
        //企业成员授权时返回示例[userid, deviceid]
        //非企业成员授权时返回示例[openid,deviceid]
        String[] res = wxMsgCpService.oauth2getUserInfo(code);
        return res[0];
    }

    @Override
    public String redirect2OauthPage(String code, Model model, HttpSession session)
            throws WxErrorException {
        String userId = this.getOauth2UserInfoIdByCode(code);
        if (session.getAttribute("user") == null){
            model.addAttribute("userId", userId);
            return "/views/wechatcp/oauth/oauth2";
        }
        return null;
    }

}
