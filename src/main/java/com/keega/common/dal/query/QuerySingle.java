package com.keega.common.dal.query;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.stereotype.Component;

import com.keega.common.dal.DalQuery;

@Component
public class QuerySingle implements DalQuery{
		
	@Resource(name="queryRunner") 
	private QueryRunner qr;

	@Override
	public <T> T query(String sql) throws SQLException {
		return qr.query(sql, new ScalarHandler<T>());
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> T query(String sql, Object params) throws SQLException {
		return qr.query(sql, params, new ScalarHandler<T>());
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> T query(String sql, Object[] params) throws SQLException {
		return qr.query(sql, params, new ScalarHandler<T>());
	}

	@Override
	public <T> List<T> queryList(String sql) throws SQLException {
		return qr.query(sql, new ColumnListHandler<T>());
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> List<T> queryList(String sql, Object[] params) throws SQLException {
		return qr.query(sql, params , new ColumnListHandler<T>());
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> List<T> queryList(String sql, Object params) throws SQLException {
		return qr.query(sql, params , new ColumnListHandler<T>());
	}
}
