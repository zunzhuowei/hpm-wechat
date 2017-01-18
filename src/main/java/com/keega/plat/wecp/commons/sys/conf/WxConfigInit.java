package com.keega.plat.wecp.commons.sys.conf;


import com.keega.plat.wecp.commons.sys.util.XMLUtil;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        /*Element waitThing = root.element("waitThing");
        Element workInfo = root.element("workInfo");
        Element myCheck = root.element("myCheck");

        Map<String, Object> waitThing_map = new HashMap<String, Object>();
        Map<String, Object> workInfo_map = new HashMap<String, Object>();
        Map<String, Object> myCheck_map = new HashMap<String, Object>();*/

        //全部包装到Map<String,Map<String,Object>>中
        //<!-- 用户登录字段设置 -->
        wxConfigData.put("user_login_map", userLoginConfig(root));
        //session 中User要存放的信息字段
        wxConfigData.put("user_config_map", sessionUserFieldConfig(root));
        //菜单要显示的内容
        wxConfigData.put("user_menus_map", menuContentConfig(root));
        //我的信息显示字段
        wxConfigData.put("myInfo_map", myInfoFieldConfig(root));
        //我的薪酬显示字段
        wxConfigData.put("mySalary_map", mySalaryFieldConfig(root));

        return wxConfigData;
    }

    //<!-- 用户登录字段设置 -->
    private static Map<String, Object> userLoginConfig(Element root) {
        Element user_login = root.element("user_login");
        Map<String, Object> user_login_map = new HashMap<String, Object>();
        user_login_map.put("username", user_login.elementTextTrim("username"));
        user_login_map.put("password", user_login.elementTextTrim("password"));
        return user_login_map;
    }

    //session 中User要存放的信息字段
    private static Map<String, Object> sessionUserFieldConfig(Element root) {
        Element user_config = root.element("user_config");
        Map<String, Object> user_config_map = new HashMap<String, Object>();
        List<Element> fields = user_config.elements();
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < fields.size(); i++) {
            if ("B0110".equals(fields.get(i).attributeValue("name"))) {
                //鉴于有时候需要中文名字，有事需要代码项，所以两个都放到session
                names.add("(select codeitemdesc from organization where codeitemid=B0110) as danwei");
                names.add(fields.get(i).attributeValue("name"));
            }else if ("E0122".equals(fields.get(i).attributeValue("name"))) {
                names.add("(select codeitemdesc from organization where codeitemid=E0122) as bumen");
                fields.get(i).attributeValue("name");
            } else if ("E01A1".equals(fields.get(i).attributeValue("name"))) {
                names.add("(select codeitemdesc from organization where codeitemid=E01A1) as gangwei");
                fields.get(i).attributeValue("name");
            } else {
                names.add(fields.get(i).attributeValue("name"));
            }
        }
        user_config_map.put("fields", names);
        return user_config_map;
    }

    //菜单要显示的内容
    private static Map<String, Object> menuContentConfig(Element root) {
        Element user_menus = root.element("user_menus");
        Map<String, Object> user_menus_map = new HashMap<String, Object>();
        List<Element> menusElements = user_menus.elements();
        List<MenuConfig> menus = new ArrayList<MenuConfig>();
        for (int i = 0; i < menusElements.size(); i++) {
            MenuConfig menuConfig = new MenuConfig();
            menuConfig.setName(menusElements.get(i).attributeValue("name"));
            menuConfig.setUrl(menusElements.get(i).attributeValue("conUrl"));
            menuConfig.setImgUrl(menusElements.get(i).attributeValue("imgUrl"));
            menuConfig.setMenuDesc(menusElements.get(i).attributeValue("desc"));
            menuConfig.setStatus(menusElements.get(i).attributeValue("status"));
            menuConfig.setMenuText(menusElements.get(i).getTextTrim());
            menus.add(menuConfig);
        }
        user_menus_map.put("menus", menus);
        return user_menus_map;
    }

    //我的信息显示字段
    private static Map<String, Object> myInfoFieldConfig(Element root) {
        Element myInfo = root.element("myInfo");
        Map<String, Object> myInfo_map = new HashMap<String, Object>();
        List<Element> myInfos = myInfo.elements();
        List<String> myInfosName = new ArrayList<String>();
        for (int i = 0; i < myInfos.size(); i++) {
            myInfosName.add(myInfos.get(i).attributeValue("name")
                    +" as "+myInfos.get(i).attributeValue("textField"));
        }
        myInfo_map.put("fields", myInfosName);
        return myInfo_map;
    }

    //我的薪酬显示字段
    private static Map<String, Object> mySalaryFieldConfig(Element root) {
        Element mySalary = root.element("mySalary");
        Map<String, Object> mySalary_map = new HashMap<String, Object>();
        List<Element> mySalarys = mySalary.elements();
        List<String> mySalaryName = new ArrayList<String>();
        for (int i = 0; i < mySalarys.size(); i++) {
            mySalaryName.add(mySalarys.get(i).attributeValue("name"));
        }
        mySalary_map.put("fields", mySalaryName);
        return mySalary_map;
    }

}
