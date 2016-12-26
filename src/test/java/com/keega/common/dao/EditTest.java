package com.keega.common.dao;

import com.keega.common.dal.Dal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-datasource.xml","classpath:spring-mvc.xml"})
public class EditTest {

	@Test
	public void 新增例子1() throws SQLException {
		Dal.del("usra01").single("a0101","姜丽").run();
	}

	@Test
	public void testQuery() throws SQLException {
		String sql = "select * from UsrA01 ";
		System.out.println(Dal.map().queryList(sql).toString());
	}


}
