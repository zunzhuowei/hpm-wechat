package com.keega.plat.wecp.controller.cp.msg;

import com.keega.plat.wecp.commons.wx.aes.AesException;
import com.keega.plat.wecp.commons.wx.menu.MenuConfig;
import com.keega.plat.wecp.controller.cp.CpController;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 微信企业公众号控制器---消息类型应用控制器
 *
 * Created by zun.wei on 2016/12/23.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/cp")
public class WxMsgTypeController extends CpController{

    @Resource
    private MenuConfig menuConfig;
    @Resource
    private WxCpConfigStorage msgConfigStorage;
    @Resource
    private WxCpService wxMsgCpService;


    //消息型验证回调
    @RequestMapping(value = "/msg/init")
    public void initHomeTypeCP(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AesException {
        super.initCpMsg(request,response);
    }

    //制作菜单
    @RequestMapping(value = "/msg/menu", method = RequestMethod.GET)
    public String makeMenus(Model model) throws WxErrorException {
        menuConfig.makeMenus();
        return "/views/wechat/menu";
    }

}
