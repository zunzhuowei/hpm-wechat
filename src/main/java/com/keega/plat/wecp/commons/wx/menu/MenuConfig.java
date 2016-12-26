package com.keega.plat.wecp.commons.wx.menu;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 企业消息型应用菜单配置
 *
 * Created by zun.wei on 2016/12/25.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Component
public class MenuConfig {

    @Resource
    private WxCpService wxMsgCpService;

    /**
     * 定义菜单结构
     *
     * @return 菜单实体类
     */
    protected WxMenu getMenu() {

       /* MainConfigMsg mainConfig = new MainConfigMsg();
        WxCpService wxCpService = mainConfig.wxCpService();*/
        WxCpService wxCpService = wxMsgCpService;

        WxMenu menu = new WxMenu();
        WxMenuButton button1 = new WxMenuButton();
        button1.setType(WxConsts.BUTTON_VIEW);
        button1.setName("首页");
        button1.setUrl(wxCpService.oauth2buildAuthorizationUrl
                ("http://161818x71d.iask.in/cp/msg/home", "state"));

        WxMenuButton button2 = new WxMenuButton();
        button2.setName("信息查询");

        WxMenuButton button21 = new WxMenuButton();
        button21.setType(WxConsts.BUTTON_CLICK);
        button21.setName("本月工资");
        button21.setKey("salary");

        WxMenuButton button22 = new WxMenuButton();
        button22.setType(WxConsts.BUTTON_VIEW);
        button22.setName("个人资料");
        button22.setUrl(wxCpService.oauth2buildAuthorizationUrl
                ("http://161818x71d.iask.in/cp/msg/info", "state"));

        WxMenuButton button221 = new WxMenuButton();
        button221.setType(WxConsts.BUTTON_VIEW);
        button221.setName("详细薪资");
        button221.setUrl(wxCpService.oauth2buildAuthorizationUrl
                ("http://161818x71d.iask.in/cp/msg/salary", "state"));

        WxMenuButton button23 = new WxMenuButton();
        button23.setType(WxConsts.BUTTON_VIEW);
        button23.setName("企业wiki");
        button23.setUrl("http://qydev.weixin.qq.com/wiki/index.php");

        WxMenuButton button24 = new WxMenuButton();
        button24.setType(WxConsts.BUTTON_VIEW);
        button24.setName("绑定HR");
        button24.setUrl(wxCpService.oauth2buildAuthorizationUrl
                ("http://161818x71d.iask.in/cp/msg/activation", "state"));

        button2.getSubButtons().add(button21);
        button2.getSubButtons().add(button22);
        button2.getSubButtons().add(button221);
        button2.getSubButtons().add(button23);
        button2.getSubButtons().add(button24);

        WxMenuButton button3 = new WxMenuButton();
        button3.setType(WxConsts.BUTTON_CLICK);
        button3.setName("使用帮助");
        button3.setKey("help");

        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        return menu;
    }

    //制作菜单
    public void makeMenus() throws WxErrorException {
        wxMsgCpService.menuCreate(getMenu());
    }

}
