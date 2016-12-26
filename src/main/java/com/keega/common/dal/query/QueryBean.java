package com.keega.common.dal.query;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Component;

@Component
public class QueryBean{
	
	@Resource(name="queryRunner") 
	private QueryRunner qr;
	
    public <T> T query(Class<T> cl, String sql) throws SQLException {       
        return qr.query(sql, new BeanHandler<T>(cl));
    }
	
    public <T> List<T> queryList(Class<T> cl, String sql) throws SQLException {       
        return qr.query(sql, new BeanListHandler<T>(cl));
    }
}
