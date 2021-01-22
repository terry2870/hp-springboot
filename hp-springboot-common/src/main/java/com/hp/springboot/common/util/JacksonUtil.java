package com.hp.springboot.common.util;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 描述：基于jackson 工具类
 * 作者：黄平
 * 时间：2020-12-22
 */
public class JacksonUtil {

	/**
	* @Title: toJSONString  
	* @Description: 对象转为json String
	* @param obj
	* @param pretty
	* @return
	 */
	public static String toJSONString(Object obj, boolean pretty) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		try {
			if (pretty) {
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
			} else {
				return mapper.writeValueAsString(obj);
			}
		} catch (JsonProcessingException e) {
			return null;
			
		}
	}
	
	/**
	* @Title: toJSONString  
	* @Description: 对象转为json String
	* @param obj
	* @return
	 */
	public static String toJSONString(Object obj) {
		return toJSONString(obj, false);
	}
	
	/**
	* @Title: parseObject  
	* @Description: json字符串转为java对象
	* @param <T>
	* @param json
	* @param clazz
	* @return
	 */
	public static <T> T parseObject(String json, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	/**
	* @Title: parseObject  
	* @Description: json字符串转为java对象
	* @param <T>
	* @param json
	* @param clazz
	* @return
	 */
	public static <T> List<T> parseArray(String json, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, new TypeReference<List<T>>() {});
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	/**
	* @Title: parse  
	* @Description: 转为为JsonNode对象
	* @param json
	* @return
	 */
	public static JsonNode parse(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readTree(json);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
