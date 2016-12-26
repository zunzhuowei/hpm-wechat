package com.keega.global;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.keega.common.dal.Dal;
import com.keega.common.memory.CodeService;
import com.keega.plat.wechat.util.config.WxConfigInit;

@WebListener
public class SystemMemoryListener implements ServletContextListener {	
	
	//服务销毁时
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//tomcat关闭了
	}


	//服务加载时
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		MemorySingle m = MemorySingle.init();
		
		System.out.println("—————————系统初始化————————————");
		//查询组织架构
		//List<Map<String, Object>> listMap = Dal.map().queryList("select codeitemid,codeitemdesc,parentid from organization where end_date > ?", "2016-12-12");

		System.out.println("|     1、组织架构加载完毕                        |");
		//m.setListMap(listMap);

		System.out.println("|     2、代码项目加载完毕                        |");
		//m.setCodeMap(new CodeService().getCodeMap());

		System.out.println("|     3、数据字典加载完毕                        |");

		System.out.println("|     4、系统参数加载完毕                        |");

		//System.out.println("|     5、WeChat configuration file init complete |");

		System.out.println("——————————————————————————");
		
	}

	
	
}
