package com.keega.plat.wecp.controller.cp.msg;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zun.wei on 2017/2/6.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/cp")
public class JssdkController {

    @Resource
    private WxCpService wxMsgCpService;

    @RequestMapping(value = "/msg/share", method = RequestMethod.GET)
    public String testJsSdk(HttpServletRequest request, HttpServletResponse response, Model model) throws WxErrorException {
        WxJsapiSignature signature = wxMsgCpService.createJsapiSignature(request.getRequestURL().toString());
        model.addAttribute("signature", signature);
        return "/views/wechatcp/js/jssdk";
    }

}
