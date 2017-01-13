package com.keega.plat.wecp.dao;

import com.keega.plat.wecp.commons.sys.conf.WxConfigInit;

import java.util.List;
import java.util.Map;

/**
 * 基本data access object
 * Created by zun.wei on 2017/1/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IBaseDao {

    //从内存中获取用户名、密码对应的字段名称
    String userName = (String) ((Map<String, Object>) WxConfigInit.getWxConfigData().
            get("user_login_map")).get("username");//用户名字段
    String passWord = (String) ((Map<String, Object>) WxConfigInit.getWxConfigData().
            get("user_login_map")).get("password");//用户密码字段
    List<String> listUserItems = (List<String>) WxConfigInit.getWxConfigData().
            get("user_config_map").get("fields");//session中用户包含的字段
    /** 人员信息要查询的字段 */
    List<String> userItems = (List<String>) WxConfigInit.getWxConfigData()
            .get("myInfo_map").get("fields");
    List<String> salaryItems = (List<String>) WxConfigInit.getWxConfigData().get("mySalary_map")
            .get("fields");

}
