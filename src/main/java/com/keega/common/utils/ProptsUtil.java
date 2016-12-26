package com.keega.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProptsUtil {
	
	
	public static Properties prop(String propertiesPath){
		Properties prop = new Properties();
		InputStream in = ProptsUtil.class.getResourceAsStream(propertiesPath);
		try {
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public static String getString(Properties prop,String key){
		return prop.getProperty(key).trim();
	}
	
	public static long getLong(Properties prop,String key){
		return Long.parseLong(prop.getProperty(key).trim());
	}
	
	public static int getInt(Properties prop,String key){
		return Integer.parseInt(prop.getProperty(key).trim());
	}
}
