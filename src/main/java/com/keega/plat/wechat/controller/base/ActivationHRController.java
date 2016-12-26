package com.keega.plat.wechat.controller.base;

import com.keega.plat.wechat.service.ouser.ISNSUserService;
import com.keega.plat.wechat.service.sys.ISysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

/**
 * 微信用户授权信息给系统控制器
 * Created by zun.wei on 2016/12/14.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/wx")
public class ActivationHRController {

    @Resource
    private ISNSUserService isnsUserService;
    @Resource
    private ISysUserService sysUserService;

    //直接访问这个控制器，会报空指针。此控制器必须由微信端redirect过来。
    @RequestMapping(value = "/activation", method = RequestMethod.GET)
    public ModelAndView activationHR(HttpServletRequest request, HttpServletResponse response
            ,ModelAndView modelAndView)throws UnsupportedEncodingException {
        Map<String, Object> userInfo = isnsUserService.getUserInfo(request, response);
        modelAndView.addObject("snsUserInfo", userInfo.get("snsUserInfo"));
        modelAndView.addObject("state", userInfo.get("state"));
        //System.out.println(userInfo.get("state"));
        modelAndView.setViewName("/views/wechat/activation/activation");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/activation", method = RequestMethod.POST)
    public String checkSysUser(@RequestParam(name = "openId") String openId,
                               @RequestParam(name = "account") String account,
                               @RequestParam(name = "password") String password,
                               HttpSession session) throws SQLException {
        return sysUserService.execBind(openId,account,password,session);
    }


}
