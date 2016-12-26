package com.keega.plat.wecp.service.core.msg;

import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

/**
 * 视图服务层接口
 *
 * Created by zun.wei on 2016/12/26.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IViewsService {

    /**
     * 根据code获取授权用户的userID/OpenID
     * @param code redirect 的uri获得的参数
     * @return userId/Openid
     */
    String getOauth2UserInfoIdByCode(String code) throws WxErrorException;

    /**
     *
     * @param code redirect 的uri获得的参数
     * @param model springmvc的model
     * @return 如果是null,表示数据库存在这个userId，如果不为null表示返回重定向的uri
     * @throws WxErrorException 异常
     */
    String redirect2OauthPage(String code, Model model, HttpSession session) throws WxErrorException;
}
