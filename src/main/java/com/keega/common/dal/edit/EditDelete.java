package com.keega.common.dal.edit;

import java.sql.SQLException;
import org.springframework.stereotype.Component;

@Component
public class EditDelete extends Edit{

	public int run() throws SQLException{
		//主体sql
		String sql = "DELETE " + tableName + " WHERE "+fieldName+" = ?";
		//如果为是批sql处理，那么执行批处理操作
		if (batchFalg) {
			return this.batchExcute(sql, values);
		}
		//否则执行单行操作
		return this.excute(sql, fieldValue);
				
	}
	
	public EditDelete from(String tableName){
		this.tableName = tableName;
		return this;
	}
	
	public EditDelete single(String fieldName,Object fieldValue){
		this.batchFalg = false;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		return this;
	}
	
	public EditDelete many(String fieldName,Object... values){
		this.batchFalg = true;
		this.fieldName = fieldName;
		this.values = values;
		return this;
	}
}
