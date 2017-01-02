package com.keega.plat.wecp.controller.cp.msg;

import com.keega.plat.wecp.service.core.msg.ICoreServiceMsg;
import com.keega.plat.wecp.service.core.msg.IViewsService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
    private WxCpService wxMsgCpService;
    @Resource
    private IViewsService viewsService;
    @Resource
    private ICoreServiceMsg coreServiceMsg;

    //视图首页
    @RequestMapping(value = "/msg/home" , method = RequestMethod.GET)
    public String home(@RequestParam(name = "code") String code, Model model,
                       HttpSession session) throws WxErrorException {
        //TODO 检查是否用用户存在再跳转
        String page = viewsService.redirect2OauthPage(code, model, session);
        if (page != null)
            return page;
        //TODO 用userId查询数据库取出相应的user，然后放到session中
        return "/views/wechatcp/home";
    }

    //我的信息
    @RequestMapping(value = "/msg/info", method = RequestMethod.GET)
    public String userInfo(@RequestParam(name = "code") String code,
                           Model model, HttpSession session) throws WxErrorException {
        String page = viewsService.redirect2OauthPage(code, model, session);
        if (page != null)
            return page;
        return "/views/wechatcp/userInfo";
    }

    @ResponseBody//异步获取用户信息
    @RequestMapping(value = "/msg/get/info", method = RequestMethod.POST)
    public String getUserInfo(@RequestParam(name = "A0100") String A0100) {
        System.out.println("A0100 = " + A0100);
        return "用户信息";
    }

    //我的薪资
    @RequestMapping(value = "/msg/salary",method = RequestMethod.GET)
    public String salaryInfo(@RequestParam(name = "code") String code,
                             Model model, HttpSession session) throws WxErrorException {
        String page = viewsService.redirect2OauthPage(code, model, session);
        if (page != null)
            return page;
        return "/views/wechatcp/userInfo";
    }

    @ResponseBody//获取薪资日期
    @RequestMapping(value = "/msg/get/date",method = RequestMethod.POST)
    public String getSalaryDate(@RequestParam(name = "A0100") String A0100) {
        System.out.println("A0100 = " + A0100);
        return "薪资日期";
    }

    @ResponseBody//获取薪资信息
    @RequestMapping(value = "/msg/get/salary",method = RequestMethod.POST)
    public String getSalaryInfo(@RequestParam(name = "A0100") String A0100
                                ,@RequestParam(name = "salaryDate") String salaryDate) {
        System.out.println("A0100 = " + A0100);
        System.out.println("salaryDate = " + salaryDate);
        return "我的薪资信息";
    }

    //绑定HR
    @RequestMapping(value = "/msg/activation", method = RequestMethod.GET)
    public String activation(@RequestParam(name = "code") String code,
                             Model model,HttpSession session) throws WxErrorException {
        String userId = viewsService.getOauth2UserInfoIdByCode(code);
        model.addAttribute("userId", userId);
        return "/views/wechatcp/oauth/oauth2";
    }

}
