package com.hp.springboot.common.constant;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;

/**
 * 描述：google的一些常用工具
 * 作者：黄平
 * 时间：2020-12-26
 */
public class GoogleConvertContant {

	/**
	 * 首字母小写驼峰转小写的下划线
	 * lowerCamel - lower_camel
	 */
	public static final Converter<String, String> LOWER_CAMEL_TO_LOWER_UNDERSCORE_CONVERTER = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);
	
	/**
	 * 首字母大写驼峰转小写的下划线
	 * LowerCamel - lower_camel
	 */
	public static final Converter<String, String> UPPER_CAMEL_TO_LOWER_UNDERSCORE_CONVERTER = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);
}
