package com.keega.global;

import java.util.List;
import java.util.Map;

public class MemorySingle {
	
	static MemorySingle m;
	
	//组织架构
	private List<Map<String, Object>> listMap;
	
	//代码项目
	private Map<String, List<CodeItem>> codeMap;
	
	public static MemorySingle init(){
		if (m == null) {
			synchronized (MemorySingle.class) {
				if (m == null) {
					m = new MemorySingle();
				}
				return m;
			}
		}
		return m;
	}

	public List<Map<String, Object>> getListMap() {
		return listMap;
	}

	public void setListMap(List<Map<String, Object>> listMap) {
		this.listMap = listMap;
	}

	public Map<String, List<CodeItem>> getCodeMap() {
		return codeMap;
	}

	public void setCodeMap(Map<String, List<CodeItem>> codeMap) {
		this.codeMap = codeMap;
	}
}
