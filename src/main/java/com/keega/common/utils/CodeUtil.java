package com.keega.common.utils;

import java.util.List;
import java.util.Map;

import com.keega.global.CodeItem;
import com.keega.global.MemorySingle;

public class CodeUtil {
	
	static Map<String, List<CodeItem>> codeMap = MemorySingle.init().getCodeMap();
	
	public static void codeReload() {
		
	}
	
	/**
	 * 得到一个代码类集合
	 * @param codeSetId
	 * 			代码分类
	 * @return List<CodeItem>
	 */
	public static List<CodeItem> getCodeItems(String codeSetId) {
		List<CodeItem> codeItemList = codeMap.get(codeSetId);
		return codeItemList;
	}
	
	public static CodeItem getCodeItem(String codeSetId,String codeItemId) {		
		List<CodeItem> codeItemList = codeMap.get(codeSetId);
		for (CodeItem codeItem : codeItemList) {
			if (codeItem.getCodeItemId().equals(codeItemId)) {
				return codeItem;
			}
		}
		return null;
	}
	
}
