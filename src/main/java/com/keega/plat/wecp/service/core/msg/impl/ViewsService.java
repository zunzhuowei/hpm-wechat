package com.keega.plat.wecp.service.core.msg.impl;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wecp.commons.sys.conf.MenuConfig;
import com.keega.plat.wecp.service.core.msg.IMapService;
import com.keega.plat.wecp.service.core.msg.IViewsService;
import com.keega.plat.wecp.service.sys.ICpSysUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    @Resource
    private ICpSysUserService cpsysUserService;
    @Resource
    private IMapService mapService;

    @Override//根据code获取授权用户的userID/OpenID
    public String getOauth2UserInfoIdByCode(String code) throws WxErrorException {
        //企业成员授权时返回示例[userid, deviceid]
        //非企业成员授权时返回示例[openid,deviceid]
        String[] res = wxMsgCpService.oauth2getUserInfo(code);
        return res[0];
    }

    @Override
    public String redirect2OauthPage(String code, Model model, HttpSession session)
            throws WxErrorException, SQLException {
        if (code == null && session.getAttribute("user") == null) {
            return "/views/error/404";
        } else if (code == null && session.getAttribute("user") != null) {
            return null;
        }
        if (session.getAttribute("user") == null) {
            String userId = this.getOauth2UserInfoIdByCode(code);
            if (cpsysUserService.hasBindDB(userId)) {
                Map<String, Object> user = cpsysUserService.getSysUserByCpUserId(userId);
                session.setAttribute("user", user);
                return null;
            } else {
                model.addAttribute("userId", userId);//用于绑定账号时,把企业号用户的userId带到页面
                return "/views/wechatcp/oauth/oauth2";
            }
        } else {
            String userId = this.getOauth2UserInfoIdByCode(code);
            if (!cpsysUserService.hasBindDB(userId)) {
                model.addAttribute("userId", userId);//用于绑定账号时,把企业号用户的userId带到页面
                return "/views/wechatcp/oauth/oauth2";
            }
        }
        return null;
    }

    @Override
    public String bindHr(String openId, String account, String password,HttpSession session) throws SQLException {
        if ("".equals(password)) password = null;
        Map<String,Object> sysOpenId = cpsysUserService.getOpenIdByAccPss(account, password);
        if (sysOpenId == null || sysOpenId.get("openid") == null) {//说明这个用户不存在
            return "04";//密码或者账号错误！
        }
        if (!"default_id".equals(sysOpenId.get("openid"))) {//说明已经有openId绑定了。
            return "03";//已经绑定了
        } else if ("default_id".equals(sysOpenId.get("openid"))){//如果没有绑定
            if (cpsysUserService.checkWeChatUserBindSysByOpenId(openId)) {
                return "05";//说明该微信账号已经绑定过账号了，不能再绑定了。
            }
            cpsysUserService.bindHrSysUser(openId,account);
            session.setAttribute("user",cpsysUserService.getSysUserByCpUserId(openId));
            return "01";//绑定成功
        }
        return "02";//绑定失败
    }

    @Override
    public String getCustomMenus(String a0100, List<MenuConfig> menus) throws SQLException {
        boolean isManager = mapService.isManagerAccountLogin(a0100);
        List<MenuConfig> menuConfigList = new ArrayList<MenuConfig>();
        if (!isManager) {//如果不是经理登录
            Iterator<MenuConfig> it = menus.iterator();
            while (it.hasNext()) {
                MenuConfig menuConfig = it.next();
                //如果不是经理登录，筛选掉经理菜单
                if (!"1".equals(menuConfig.getStatus())) menuConfigList.add(menuConfig);
            }
            return JsonUtil.obj2json(menuConfigList);
        }
        return JsonUtil.obj2json(menus);
    }

    @Override
    public String secondBindHr(String openId, String account, String password, HttpSession session) throws SQLException, WxErrorException {
        if ("".equals(password)) password = null;
        Map<String,Object> sysOpenId = cpsysUserService.getOpenIdByAccPss(account, password);
        if (sysOpenId == null || sysOpenId.get("openid") == null) {//说明这个用户不存在
            return "04";//密码或者账号错误！
        }
        if (!"default_id".equals(sysOpenId.get("openid"))) {//说明已经有openId绑定了。
            return "03";//已经绑定了
        }
        if ("default_id".equals(sysOpenId.get("openid"))){//如果没有绑定
            if (cpsysUserService.checkWeChatUserBindSysByOpenId(openId)) {
                return "05";//说明该微信账号已经绑定过账号了，不能再绑定了。
            }
            cpsysUserService.bindHrSysUser(openId,account);
            session.setAttribute("user",cpsysUserService.getSysUserByCpUserId(openId));
            //https://qyapi.weixin.qq.com/cgi-bin/user/authsucc?access_token=ACCESS_TOKEN&userid=USERID
            //String url = "https://qyapi.weixin.qq.com/cgi-bin/user/authsucc?userid=" + userId;
            wxMsgCpService.userAuthenticated(openId);//返回给微信服务器
            return "01";//绑定成功
        }
        return "02";//绑定失败
    }

}
