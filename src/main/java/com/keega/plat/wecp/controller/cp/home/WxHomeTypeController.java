package com.keega.plat.wecp.controller.cp.home;

import com.keega.plat.wecp.commons.wx.aes.AesException;
import com.keega.plat.wecp.controller.cp.CpController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信企业公众号控制器---主页类型应用控制器
 * Created by zun.wei on 2016/12/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/cp")
public class WxHomeTypeController extends CpController{

    //主页型验证回调
    @RequestMapping(value = "/home/init")
    public void initHomeTypeCP(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AesException {
        super.initCpHome(request,response);
    }

    //主页型，企业号中配置的首页访问地址。
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String redirectLogin() {

        return "/views/login";
    }

}
