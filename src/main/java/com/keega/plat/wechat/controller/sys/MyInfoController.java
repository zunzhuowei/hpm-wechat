package com.keega.plat.wechat.controller.sys;

import com.keega.plat.wechat.service.sys.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

/**
 * 我的个人信息控制器
 * Created by zun.wei on 2016/12/12.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/self/info")
public class MyInfoController {

    @Resource
    private IUserService userService;

    @RequestMapping(value ="/home",method = RequestMethod.GET)
    public String selfInfoHome(HttpSession session) {
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
        if (user == null) return "/views/error/404";
        return "/views/wechat/userInfo";
    }

    @ResponseBody
    @RequestMapping(value = "/get/info",method = RequestMethod.POST)
    public String showUserInfo(@RequestParam(name = "A0100") String A0100) throws SQLException {
        if ("".equals(A0100)) return null;
        return userService.getJsonUserInfoByA0100(A0100);
    }

}
