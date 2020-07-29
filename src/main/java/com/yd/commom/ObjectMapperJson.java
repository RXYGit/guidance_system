package com.yd.commom;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperJson {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//对象转化Json
	public static String toJson(Object obj) {
		String  result = null;
		try {
			result =  MAPPER.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return result;
	}
	
	//json转对象
	public static <T> T toString(String json,Class<T> tagetClass) {
		T t = null;
		try {
			t = MAPPER.readValue(json, tagetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
}
