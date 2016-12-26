package com.keega.common.memory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.keega.common.dal.Dal;
import com.keega.global.CodeItem;

public class CodeService {
	
	public Map<String, List<CodeItem>> getCodeMap() throws SQLException{
		Map<String, List<CodeItem>> codeMap = new HashMap<String, List<CodeItem>>();
		//查询代码分类
		List<String> codeSetIdList = Dal.single().queryList("select codeSetId from CodeSet");
		for (String codeSetId : codeSetIdList) {
			//按分类查询实际的代码项目
			List<CodeItem> codeItemList = Dal.bean().queryList(CodeItem.class,"select codeitemid,codeitemdesc,parentid from codeitem where codeSetId = '"+codeSetId+"'");
			codeMap.put(codeSetId, codeItemList);
		}
		return codeMap;
	}
	
	

	
}
