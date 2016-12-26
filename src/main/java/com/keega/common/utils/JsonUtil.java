package com.keega.common.utils;

import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *<p>json工具类</p>
 * @author Administrator
 * @version V1.0
 * @see 相关的类
 */
public class JsonUtil {
	
	/**
	 * 任意对象转换json
	 * @param obj
	 * @return
	 */
	public static String obj2json(Object obj) {
		return new Gson().toJson(obj);
	}
	
	/**
	 * json转换对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static Object json2Obj(String json,Class<?> cls){	
		return new Gson().fromJson(json,cls);
	}
	
	/**
	 * json转换对象集合
	 * @param jso
	 * @return
	 */
	public static List<Object> json2ObjList(String json){	
		return new Gson().fromJson(json,new TypeToken<List<Object>>(){}.getType());
	}
	
	/**
	 * json转换map
	 * @param jso
	 * @return
	 */
	public static Map<String, Object> json2Map(String json){	
		return new Gson().fromJson(json,new TypeToken<Map<String, Object>>(){}.getType());
	}
	
	/**
	 * Json转换List<map>
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> json2MapList(String json){
		return new Gson().fromJson(json,new TypeToken<List<Map<String, Object>>>(){}.getType());
	}
	
	/**
	 * Json转换Arrays
	 * @param json
	 * @return
	 */
	public static Object[] json2Arrays(String json){
		
		return new Gson().fromJson(json,new TypeToken<Object[]>(){}.getType());
	}
	
	public static void main(String[] args) {
		String json = "[{\"id\":\"1000\"}]";
		List<Map<String, Object>> listmap = JsonUtil.json2MapList(json);
		
		System.out.println(JsonUtil.obj2json(listmap));
	}
}