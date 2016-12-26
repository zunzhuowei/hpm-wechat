package com.keega.plat.wechat.controller.sys;

import com.keega.plat.wechat.service.sys.ISalaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

/**
 * 我的薪酬控制器
 * Created by zun.wei on 2016/12/12.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Controller
@RequestMapping(value = "/self/salary")
public class MySalaryController {

    @Resource
    private ISalaryService salaryService;

    //我的薪资首页
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String selfSalaryHome(Model model, HttpSession session) {
        Map<String,Object> user = (Map<String, Object>) session.getAttribute("user");
        if (user == null) return "/views/error/404";
        return "/views/wechat/salary";
    }

    @ResponseBody//赋值给筛选薪资日期的
    @RequestMapping(value = "/send/date",method = RequestMethod.GET)
    public String showSendDate(@RequestParam(name = "A0100") String A0100) throws SQLException {
        return salaryService.getSendSalaryDateJsonByA0100(A0100);
    }

    @ResponseBody//根据薪资日期筛选薪资
    @RequestMapping(value = "/get/list",method = RequestMethod.POST)
    public String getSalaryList(@RequestParam(name = "A0100") String A0100,
                                @RequestParam(name = "salaryDate") String salaryDate) throws SQLException {
        return salaryService.getSalaryInfoByDateA0100(A0100, salaryDate);
    }

}
