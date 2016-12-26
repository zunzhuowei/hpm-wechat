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

    //auth2授权
    @ResponseBody//这个用来测试，发送“授权”，回复一个授权uri
    @RequestMapping(value = "/msg/auth2")//redirect 过来的地址
    public void auth2(HttpServletRequest request, HttpServletResponse response)
            throws WxErrorException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String code = request.getParameter("code");
        response.getWriter().println("<h1>code</h1>");
        response.getWriter().println(code);

        String[] res = this.wxMsgCpService.oauth2getUserInfo(code);
        response.getWriter().println("<h1>result</h1>");
        response.getWriter().println(Arrays.toString(res));

        response.getWriter().flush();
        response.getWriter().close();
        //return "/views/wechat/menu";
    }

}
