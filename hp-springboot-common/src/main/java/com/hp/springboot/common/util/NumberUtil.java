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
	 * @Title: isNull
	 * @Description: 判断是否是null
	 * @param value
	 * @return
	 */
	public static boolean isNull(Number value) {
		return value == null;
	}
	
	/**
	 * @Title: isNotNull
	 * @Description: 判断不是null
	 * @param value
	 * @return
	 */
	public static boolean isNotNull(Number value) {
		return !isNull(value);
	}
	
	/**
	 * @Title: isZero
	 * @Description: 判断是0
	 * @param value
	 * @return
	 */
	public static boolean isZero(Number value) {
		BigDecimal b = new BigDecimal(value.toString());
		return b.compareTo(BigDecimal.ZERO) == 0;
	}
	
	/**
	 * @Title: isNotZero
	 * @Description: 判断不是0
	 * @param value
	 * @return
	 */
	public static boolean isNotZero(Number value) {
		return !isZero(value);
	}
	
	/**
	 * @Title: isNullOrZero
	 * @Description: 判断是null 或者 是0
	 * @param value
	 * @return
	 */
	public static boolean isNullOrZero(Number value) {
		return isNull(value) || isZero(value);
	}
	
	/**
	 * @Title: isNotNullAndNotZero
	 * @Description: 判断既不是null，又不是0
	 * @param value
	 * @return
	 */
	public static boolean isNotNullAndNotZero(Number value) {
		return !isNullOrZero(value);
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
