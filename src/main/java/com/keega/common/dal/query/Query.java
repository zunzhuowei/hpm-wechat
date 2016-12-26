package com.keega.common.dal.query;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;

public abstract class Query {
	
	@Resource protected QueryRunner qr;
	
	protected String tableName;
	protected String columns = null;
	protected String where = null;
	protected Object[] whereValues = null;
	
	public String bulidSql(){
		//主体sql
		String sql = "SELECT %1$s FROM %2$s WHERE 1 = 1 %3$s";
		if (where == null) {
			sql= String.format(sql, columns,tableName,"");
		}else{
			sql = String.format(sql, columns,tableName,where);
		}
		return sql;
		
	}
}
