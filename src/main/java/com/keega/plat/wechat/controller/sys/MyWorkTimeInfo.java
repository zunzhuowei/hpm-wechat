package com.keega.plat.wechat.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 我的工作时长控制器
 * Created by zun.wei on 2016/12/12.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/work/time")
public class MyWorkTimeInfo {

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String workTimeHome() {

        return "/views/wechat/worktime";
    }
}
