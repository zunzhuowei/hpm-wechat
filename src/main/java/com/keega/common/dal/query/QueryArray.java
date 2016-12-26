package com.keega.common.dal.query;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.springframework.stereotype.Component;

import com.keega.common.dal.DalQuery;

@SuppressWarnings("unchecked")
@Component
public class QueryArray implements DalQuery {
	
	@Resource(name="queryRunner") 
	private QueryRunner qr;
		
	@Override
	public Object[] query(String sql) throws SQLException {
		return  qr.query(sql, new ArrayHandler());
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object[] query(String sql, Object params) throws SQLException {
		return qr.query(sql, params, new ArrayHandler());
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object[] query(String sql, Object[] params) throws SQLException {
		return qr.query(sql, params, new ArrayHandler());
	}

	@Override
	public List<Object[]> queryList(String sql) throws SQLException {
		return qr.query(sql, new ArrayListHandler());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Object[]> queryList(String sql, Object[] params) throws SQLException {
		return qr.query(sql, params, new ArrayListHandler());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Object[]> queryList(String sql, Object params) throws SQLException {
		return qr.query(sql, params, new ArrayListHandler());
	}
	
}
