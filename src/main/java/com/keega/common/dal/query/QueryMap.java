package com.keega.common.dal.query;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.stereotype.Component;

import com.keega.common.dal.DalQuery;

@SuppressWarnings("unchecked")
@Component("queryMap")
public class QueryMap extends Query implements DalQuery{
	
	@Resource(name="queryRunner") 
	private QueryRunner qr;
	
	@Override
	public Map<String, Object> query(String sql) throws SQLException {
		return qr.query(sql, new MapHandler());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> query(String sql, Object params) throws SQLException {
		return qr.query(sql, params ,new MapHandler());
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, Object> query(String sql, Object[] params) throws SQLException {
		return qr.query(sql, params ,new MapHandler());
	}

	@Override
	public List<Map<String, Object>> queryList(String sql) throws SQLException {
		return qr.query(sql,new MapListHandler());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Map<String, Object>> queryList(String sql, Object[] params) throws SQLException {
		return qr.query(sql,params,new MapListHandler());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Map<String, Object>> queryList(String sql, Object params) throws SQLException {
		return qr.query(sql,params,new MapListHandler());
	}
	
	
}
