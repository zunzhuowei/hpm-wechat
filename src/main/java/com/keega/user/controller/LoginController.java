package com.keega.user.controller;

import com.keega.common.dal.Dal;
import com.keega.user.service.Test2222;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"ouser"})
public class LoginController {
	
	@Resource Test2222 test2222;
	
	
	/**
	 * 进入登录界面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public String login() throws Exception{
		//System.out.println("I'm login success ");
		//String sql = "select * from UsrA01 where openid=?";
		//List<Map<String,Object>> mapList = Dal.map().queryList(sql,"default_id");
		//System.out.println(mapList.toString());//map的key区分大小写
		//System.out.println("mapList = " + mapList.size());
		//System.out.println(mapList.get(0).toString());
		return "/views/login";
	}
	
	/**
	 * 校验用户
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "login/vaild",method=RequestMethod.POST)
	public @ResponseBody String loginVaild(String username,String password){
		//TODO 1、校验用户；1.1、校验用户是否存在、1.2校验密码是否正确
		//select password from ouser where username = '张三'
		//如果值正确得到一个password
		
		return "返回验证信息";
	}
	
	/**
	 * 执行登录到首页
	 * @return
	 */
	//@Authority
	@RequestMapping(value = "login/home",method=RequestMethod.POST)
	public String loginHome(){
		return "/views/home";
	}
	
	
}
