package com.keega.plat.wecp.controller.cp.msg;

import com.keega.plat.wecp.commons.sys.conf.MenuConfig;
import com.keega.plat.wecp.commons.sys.conf.WxConfigInit;
import com.keega.plat.wecp.service.core.msg.ICpSalaryService;
import com.keega.plat.wecp.service.core.msg.ICpUserService;
import com.keega.plat.wecp.service.core.msg.IViewsService;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 消息型授权控制器
 *
 * Created by zun.wei on 2016/12/26.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/cp")
public class MOauth2ViewController {

    @Resource
    private IViewsService viewsService;
    @Resource
    private ICpUserService cpUserService;
    @Resource
    private ICpSalaryService cpSalaryService;

    /** 首页菜单 */
    private static final List<MenuConfig> menus = (List<MenuConfig>) WxConfigInit.getWxConfigData()
            .get("user_menus_map").get("menus");

   /* @PostConstruct
    private void init() {}*/

    //视图首页
    @RequestMapping(value = "/msg/home" , method = RequestMethod.GET)
    public String home(@RequestParam(name = "code") String code, Model model,
                       HttpSession session) throws WxErrorException, SQLException {
        String page = viewsService.redirect2OauthPage(code, model, session);
        if (page != null) return page;
        return "/views/wechatcp/home";
    }

    //异步获取不同角色该有的菜单
    @ResponseBody
    @RequestMapping(value = "/msg/get/custom/menus", method = RequestMethod.POST)
    public String getCustomMenus(@RequestParam(name = "A0100") String A0100) throws SQLException {
        return viewsService.getCustomMenus(A0100,menus);
    }

    //前往我的个人信息页面
    @RequestMapping(value = "/msg/info", method = RequestMethod.GET)
    public String userInfo(@RequestParam(name = "code",required = false) String code,
                           Model model, HttpSession session) throws WxErrorException, SQLException {
        String page = viewsService.redirect2OauthPage(code, model, session);
        if (page != null) return page;
        model.addAttribute("ajaxUrl", "/cp/msg/get/info");
        return "/views/wechatcp/userInfo";
    }

    @ResponseBody//异步获取用户信息
    @RequestMapping(value = "/msg/get/info", method = RequestMethod.POST)
    public String getUserInfo(@RequestParam(name = "A0100") String A0100) throws SQLException {
        if ("".equals(A0100)) return null;
        return cpUserService.getJsonUserInfoByA0100(A0100);
    }

    //前往我的薪资页面
    @RequestMapping(value = "/msg/salary",method = RequestMethod.GET)
    public String salaryInfo(@RequestParam(name = "code",required = false) String code,
                             Model model, HttpSession session) throws WxErrorException, SQLException {
        String page = viewsService.redirect2OauthPage(code, model, session);
        if (page != null) return page;
        model.addAttribute("dateUrl", "/cp/msg/get/date");
        model.addAttribute("getSalaryUrl", "/cp/msg/get/salary");
        return "/views/wechatcp/salary";
    }

    @ResponseBody//获取薪资日期
    @RequestMapping(value = "/msg/get/date",method = RequestMethod.GET)
    public String getSalaryDate(@RequestParam(name = "A0100") String A0100) throws SQLException {
        return cpSalaryService.getSendSalaryDateJsonByA0100(A0100);
    }

    @ResponseBody//获取薪资信息
    @RequestMapping(value = "/msg/get/salary",method = RequestMethod.POST)
    public String getSalaryInfo(@RequestParam(name = "A0100") String A0100
                                ,@RequestParam(name = "salaryDate") String salaryDate) throws SQLException {
        return cpSalaryService.getSalaryInfoByDateA0100(A0100, salaryDate);
    }

    //进入绑定HR页面
    @RequestMapping(value = "/msg/activation", method = RequestMethod.GET)
    public String activation(@RequestParam(name = "code") String code,
                             Model model,HttpSession session) throws WxErrorException {
        String userId = viewsService.getOauth2UserInfoIdByCode(code);
        model.addAttribute("userId", userId);
        return "/views/wechatcp/oauth/oauth2";
    }

    //ajax异步获取绑定hr的参数
    @ResponseBody
    @RequestMapping(value = "/msg/try/act", method = RequestMethod.POST)
    public String activation(@RequestParam(name = "openId") String openId,
                             @RequestParam(name = "account") String account,
                             @RequestParam(name = "password") String password,
                             HttpSession session) throws SQLException {
        return viewsService.bindHr(openId, account, password,session);
    }

    //微信二次验证入口
    @RequestMapping(value = "/msg/second/activation", method = RequestMethod.GET)
    public String secondActivation(@RequestParam(name = "code") String code,Model model) throws WxErrorException {
        String userId = viewsService.getOauth2UserInfoIdByCode(code);
        model.addAttribute("userId", userId);
        return "/views/wechatcp/oauth/secondOauth2";
    }

    //微信二次验证Ajax数据提交方法
    @ResponseBody
    @RequestMapping(value = "/msg/try/second/act",method = RequestMethod.POST)
    public String secondActivation(@RequestParam(name = "openId") String openId,
                                   @RequestParam(name = "account") String account,
                                   @RequestParam(name = "password") String password,
                                   HttpSession session) throws SQLException, WxErrorException {
        return viewsService.secondBindHr(openId, account, password,session);
    }

    //激活成功之后登陆到的页面
    @RequestMapping(value = "/msg/ac/su",method = RequestMethod.GET)
    public String activationSuccessPage(HttpSession session, Model model) throws SQLException {
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
        if (user == null) return "/views/error/404";
        else {
            return "/views/wechatcp/home";
        }
    }


}
