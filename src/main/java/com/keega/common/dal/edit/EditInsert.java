package com.keega.common.dal.edit;

import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EditInsert extends Edit{

	public int run() throws SQLException{
		//主体sql
		String sql = "INSERT INTO " + tableName + "(%1$s) VALUES(%2$s)";
		
		//sql包含的列
		StringBuffer sql_columns = new StringBuffer();		
		//sql的参数
		StringBuffer sql_params = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			sql_columns.append(columns[i]+",");
			sql_params.append("?,");
		}
		
		//去除列拼装中的最后一个逗号，拼接到主体sql中
		sql_columns.deleteCharAt(sql_columns.lastIndexOf(","));
		//去除参数拼装中的最后一个逗号，拼接到主体sql中
		sql_params.deleteCharAt(sql_params.lastIndexOf(","));
		
		//如果为是批sql处理，那么执行批处理操作
		if (batchFalg) {
			return this.batchExcute(String.format(sql, sql_columns,sql_params), listValues);
		}
		//否则执行单行操作
		return this.excute(String.format(sql, sql_columns,sql_params), values);		
	}

	public EditInsert into(String tableName){
		this.tableName = tableName;
		return this;
	}
	
	public EditInsert column(String...columns){
		this.columns = columns;
		return this;
	}
	
	public EditInsert values(Object...values){
		this.batchFalg = false;
		this.values = values;
		return this;
	}
	
	public EditInsert values(List<Object[]> listValues){
		this.batchFalg = true;
		this.listValues = listValues;
		return this;
	}		
}
