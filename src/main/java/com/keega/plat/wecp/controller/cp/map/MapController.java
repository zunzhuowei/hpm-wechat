package com.keega.plat.wecp.controller.cp.map;

import com.keega.common.utils.JsonUtil;
import com.keega.plat.wecp.service.core.msg.IMapService;
import com.keega.plat.wecp.service.core.msg.IViewsService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

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
    @Resource
    private IViewsService viewsService;

    //员工签到页面
    @RequestMapping(value = "/msg/to/map", method = RequestMethod.GET)
    public String go2CheckWork(@RequestParam(name = "code") String code,
                               Model model, HttpSession session) throws WxErrorException, SQLException {
        String page = viewsService.redirect2OauthPage(code, model, session);
        if (page != null) return page;
        return "/views/wechatcp/map/checkwork";
    }

    //领导设置签到地址页面
    @RequestMapping(value = "/msg/make/map", method = RequestMethod.GET)
    public String go2MakeMap(Model model) {
        //TODO 可以设置权限访问相应的页面
        return "/views/wechatcp/map/makemap";
    }

    //将考勤的坐标点转成范围显示
    @RequestMapping(value = "/msg/show/map", method = RequestMethod.GET)
    public String showPoints2Map(Model model) throws SQLException {
        model.addAttribute("points",mapService.getPoints2Map());
        return "/views/wechatcp/map/points2map";
    }

    //员工考勤列表1
    /*@RequestMapping(value = "/msg/show/check/list", method = RequestMethod.GET)
    public String showEmployeeCheckInfo() {
        //1.菜单验证经理身份才能显示这个菜单。
        //2.其次，需要验证数据库经理身份，才能进入，防止直接用url登录。
        //3.查看经理查看范围权限。
        return "/views/wechatcp/map/checklist";
    }*/

    //员工考勤列表2
    @RequestMapping(value = "/msg/emp/checks",method = RequestMethod.GET)
    public String showEmpCheckWorkInfo(@RequestParam(name = "code",required = false) String code,
                                       Model model,HttpSession session) throws WxErrorException, SQLException {
        String page = viewsService.redirect2OauthPage(code, model, session);
        if (page != null) return page;
        return "/views/wechatcp/map/checklist2";
    }

    //员工个人月度账单
    @RequestMapping(value = "/msg/emp/monthlist",method = RequestMethod.GET)
    public String showEmpMonthChekcList(@RequestParam(name = "A0100") String A0100,
                                        @RequestParam(name = "A0101") String A0101,
                                        Model model){
        model.addAttribute("A0100", A0100);
        model.addAttribute("A0101", A0101);
        return "/views/wechatcp/map/monthlist";
    }

    @ResponseBody//员工签到/签退的地址
    @RequestMapping(value = "/msg/check/work", method = RequestMethod.POST)
    public String checkWordIn(@RequestParam(name = "pointJson") String pointJson,
                              @RequestParam(name = "address") String address,
                              @RequestParam(name = "user") String user,
                              @RequestParam(name = "reason",required = false) String reason) throws SQLException, ParseException {
        Map<String, Object> userMap = JsonUtil.json2Map(user);
        return mapService.checkWorkSign(pointJson,address,userMap,reason);
    }

    @ResponseBody//获取设置签到地址的范围所有点
    @RequestMapping(value = "/msg/get/points",method = RequestMethod.POST)
    public String makeMap(@RequestParam(name = "overlays") String overlays,
                          @RequestParam(name = "A0100") String A0100,
                          @RequestParam(name = "desc") String desc) throws SQLException {
        mapService.makeMap(overlays,A0100,desc);
        return "make map success";
    }

    @ResponseBody//初始化签到，验证当前时间是否开启签到/签退，是否已签到/已签退
    @RequestMapping(value = "/msg/check/work/init",method = RequestMethod.POST)
    public String initCheckWork(@RequestParam(name = "A0100") String A0100) throws SQLException, ParseException {
        return mapService.hasSignInOrOut(A0100);
    }

    @ResponseBody//获取打卡信息
    @RequestMapping(value = "/msg/get/check/info", method = RequestMethod.POST)
    public String getCheckInfo(@RequestParam(name = "A0100") String A0100) throws SQLException {
        return mapService.checkSignInfo(A0100);
    }

    @ResponseBody//查询员工考勤列表Ajax
    @RequestMapping(value = "/msg/get/check/work/infos", method = RequestMethod.POST)
    public String getEmpCheckWorkInfos(@RequestParam(name = "date",required = false) String date,
                                       @RequestParam(name = "empName",required = false) String empName,
                                       @RequestParam(name = "A0100") String A0100) throws SQLException, ParseException {
        System.out.println("date = " + date);
        return mapService.searchEmpCheckWorkInfoByDateAndA0100(date, A0100,empName);
    }

    @ResponseBody//查询员工月度考勤列表Ajax
    @RequestMapping(value = "/msg/get/check/work/month", method = RequestMethod.POST)
    public String getEmpMonthCheckWorkInfos(@RequestParam(name = "date",required = false) String date,
                                       @RequestParam(name = "A0100") String A0100) throws SQLException, ParseException {
        return mapService.searchEmpMonthCheckList(date, A0100);
    }

}
