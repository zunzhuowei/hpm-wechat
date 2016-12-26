package com.keega.plat.wechat.controller.base;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wechat.service.ouser.ISNSUserService;
import com.keega.plat.wechat.service.sys.ISysUserService;
import com.keega.plat.wechat.util.config.MenuConfig;
import com.keega.plat.wechat.util.config.WxConfigInit;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 微信菜单中的view控制器
 * Created by zun.wei on 2016/12/15.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/wx")
public class ViewsController {

    @Resource
    private ISNSUserService isnsUserService;
    @Resource
    private ISysUserService sysUserService;

    /** 首页菜单 */
    private static final List<MenuConfig> menus = (List<MenuConfig>) WxConfigInit.getWxConfigData()
            .get("user_menus_map").get("menus");

    //绩佳首页
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public ModelAndView keegaHome(HttpServletRequest request, HttpServletResponse response,HttpSession session,
                            ModelAndView modelAndView) throws UnsupportedEncodingException, SQLException {
        Map<String, Object> userInfo = isnsUserService.getUserInfo(request, response);
        sysUserService.setModelAndView(userInfo,modelAndView,session,menus);
        return modelAndView;
    }

    //激活成功之后登陆到的页面
    @RequestMapping(value = "/ac/su",method = RequestMethod.GET)
    public String activationSuccessPage(HttpSession session, Model model) {
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
        if (user == null) return "/views/error/404";
        else {
            model.addAttribute("menuJson", JsonUtil.obj2json(menus));
            return "/views/wechat/home";
        }
    }


}
