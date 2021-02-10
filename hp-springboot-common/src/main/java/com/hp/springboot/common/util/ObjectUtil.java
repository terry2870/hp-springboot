/**
 * 
 */
package com.hp.springboot.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 描述：
 * 
 * @author ping.huang
 * 2016年5月6日
 */
public class ObjectUtil {

	
	/**
	 * 执行方法
	 * 
	 * @param classObj
	 * @param method
	 * @param classArr
	 * @param paramArr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T executeJavaMethod(Object classObj, String method, Class<?>[] classArr, Object[] paramArr) throws Exception {
		if (classObj == null || StringUtils.isEmpty(method)) {
			return null;
		}
		T result = null;
		if (classObj instanceof Class) {
			Class<?> clazz = (Class<?>) classObj;
			result = (T) clazz.getMethod(method, classArr).invoke(classObj, paramArr);
		} else {
			result = (T) classObj.getClass().getMethod(method, classArr).invoke(classObj, paramArr);
		}
		return result;
	}
}
