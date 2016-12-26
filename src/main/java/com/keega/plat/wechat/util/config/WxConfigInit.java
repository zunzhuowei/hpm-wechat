package com.keega.plat.wechat.util.config;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by zun.wei on 2016/12/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class WxConfigInit {

    private static Document document;
    private static Map<String,Map<String,Object>> wxConfigData;

    public static Map<String, Map<String, Object>> getWxConfigData() {
        if (wxConfigData == null) return getWxCofigInitData();
        return wxConfigData;
    }

    /*public static void init() {
        if (wxConfigData == null) {
            synchronized (WxConfigInit.class) {
                if (wxConfigData == null) {
                    wxConfigData = getWxCofigInitData();
                }
            }
        }
    }*/

    private WxConfigInit() {}

    static {
        if (document == null) {
            document = XMLUtil.getWechatDocument();
        }
        if (wxConfigData == null) {
            wxConfigData = getWxCofigInitData();
        }
    }


    private static Map<String, Map<String, Object>> getWxCofigInitData(){
        Map<String, Map<String, Object>> wxConfigData = new HashMap<String, Map<String, Object>>();
        Element root = document.getRootElement();

        Element user_login = root.element("user_login");
        Element user_config = root.element("user_config");
        Element user_menus = root.element("user_menus");
        Element waitThing = root.element("waitThing");
        Element myInfo = root.element("myInfo");
        Element mySalary = root.element("mySalary");
        Element workInfo = root.element("workInfo");
        Element myCheck = root.element("myCheck");
        Map<String, Object> user_login_map = new HashMap<String, Object>();
        Map<String, Object> user_config_map = new HashMap<String, Object>();
        Map<String, Object> user_menus_map = new HashMap<String, Object>();
        Map<String, Object> waitThing_map = new HashMap<String, Object>();
        Map<String, Object> myInfo_map = new HashMap<String, Object>();
        Map<String, Object> mySalary_map = new HashMap<String, Object>();
        Map<String, Object> workInfo_map = new HashMap<String, Object>();
        Map<String, Object> myCheck_map = new HashMap<String, Object>();

        //<!-- 用户登录字段 -->
        user_login_map.put("username", user_login.elementTextTrim("username"));
        user_login_map.put("password", user_login.elementTextTrim("password"));

        //session 中User要存放的信息字段
        List<Element> fields = user_config.elements();
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < fields.size(); i++) {
            names.add(fields.get(i).attributeValue("name"));
        }
        user_config_map.put("fields", names);

        //菜单要显示的内容
        List<Element> menusElements = user_menus.elements();
        List<MenuConfig> menus = new ArrayList<MenuConfig>();
        for (int i = 0; i < menusElements.size(); i++) {
            MenuConfig menuConfig = new MenuConfig();
            menuConfig.setName(menusElements.get(i).attributeValue("name"));
            menuConfig.setUrl(menusElements.get(i).attributeValue("conUrl"));
            menuConfig.setMenuDesc(menusElements.get(i).attributeValue("desc"));
            menuConfig.setImgUrl(menusElements.get(i).attributeValue("imgUrl"));
            menuConfig.setMenuText(menusElements.get(i).getTextTrim());
            menus.add(menuConfig);
        }
        user_menus_map.put("menus", menus);

        //我的信息显示字段
        List<Element> myInfos = myInfo.elements();
        List<String> myInfosName = new ArrayList<String>();
        for (int i = 0; i < myInfos.size(); i++) {
            myInfosName.add(myInfos.get(i).attributeValue("name")
                    +" as "+myInfos.get(i).attributeValue("textField"));
        }
        myInfo_map.put("fields", myInfosName);

        //我的薪酬显示字段
        List<Element> mySalarys = myInfo.elements();
        List<String> mySalaryName = new ArrayList<String>();
        for (int i = 0; i < mySalarys.size(); i++) {
            mySalaryName.add(mySalarys.get(i).attributeValue("name"));
        }
        mySalary_map.put("fields", mySalaryName);

        //全部包装到Map<String,Map<String,Object>>中
        wxConfigData.put("user_login_map", user_login_map);
        wxConfigData.put("user_config_map", user_config_map);//
        wxConfigData.put("user_menus_map", user_menus_map);//user_menus_map
        wxConfigData.put("myInfo_map", myInfo_map);
        wxConfigData.put("mySalary_map", mySalary_map);

        return wxConfigData;
    }

}
