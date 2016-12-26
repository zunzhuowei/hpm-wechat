package com.keega.common.dal.edit;

import org.springframework.stereotype.Component;

@Component
public class EditUpdate extends Edit{

	public EditUpdate from(String tableName){
		this.tableName = tableName;
		return this;
	}
}
