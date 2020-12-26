package com.hp.springboot.common.util;

import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 描述：数字类型的工具类
 * 作者：黄平
 * 时间：2020-12-23
 */
public class NumberUtil {

	
	/**
	* @Title: isEmpty  
	* @Description: 为空或为0
	* @param value
	* @return
	 */
	public static boolean isEmpty(Number value) {
		if (value == null) {
			return true;
		}
		
		BigDecimal b = new BigDecimal(value.toString());
		return b.compareTo(BigDecimal.ZERO) == 0;
	}
	
	/**
	* @Title: isNotEmpty  
	* @Description: 不为空，且不为0
	* @param value
	* @return
	 */
	public static boolean isNotEmpty(Integer value) {
		return !isEmpty(value);
	}
	
	/**
	* @Title: defaultIntValue  
	* @Description: 取int值
	* @param value
	* @return
	 */
	public static int defaultIntValue(Integer value) {
		return defaultIntValue(value, NumberUtils.INTEGER_ZERO.intValue());
	}
	
	/**
	* @Title: defaultIntValue  
	* @Description: 取int值
	* @param value
	* @param dftValue
	* @return
	 */
	public static int defaultIntValue(Integer value, int dftValue) {
		if (value == null) {
			return dftValue;
		}
		return value.intValue();
	}
}
