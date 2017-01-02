package com.keega.plat.wecp.controller.cp.map;

import com.keega.plat.wecp.service.core.msg.IMapService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2016/12/29.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/cp")
public class MapController {

    @Resource
    private IMapService mapService;

    //员工签到页面
    @RequestMapping(value = "/msg/to/map", method = RequestMethod.GET)
    public String go2CheckWork(Model model) {

        return "/views/wechatcp/map/checkwork";
    }

    //领导设置签到地址页面
    @RequestMapping(value = "/msg/make/map", method = RequestMethod.GET)
    public String go2MakeMap(Model model) {

        return "/views/wechatcp/map/makemap";
    }

    //将考勤的坐标点转成范围显示
    @RequestMapping(value = "/msg/show/map", method = RequestMethod.GET)
    public String showPoints2Map(Model model) {
        model.addAttribute("points",mapService.getPoints2Map());
        return "/views/wechatcp/map/points2map";
    }

    @ResponseBody//员工签到的地址
    @RequestMapping(value = "/msg/ckeck/work",method = RequestMethod.POST)
    public String ckeckWord(String pointJson,String address){
        return mapService.ckeckWork(pointJson,address);
        //return "ckeck work success";
    }

    @ResponseBody//获取设置签到地址的范围所有点
    @RequestMapping(value = "/msg/get/points",method = RequestMethod.POST)
    public String makeMap(String overlays) {
        mapService.makeMap(overlays);
        return "make map success";
    }


}
