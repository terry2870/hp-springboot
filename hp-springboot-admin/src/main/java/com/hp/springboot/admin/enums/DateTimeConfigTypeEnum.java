package com.hp.springboot.admin.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.hp.springboot.common.bean.ValueTextBean;

/**
 * 描述：时间格式的配置类型
 * 作者：黄平
 * 时间：2021年2月24日
 */
public enum DateTimeConfigTypeEnum {

	YEAR(1, "yyyy"),
	MONTH(2, "yyyy-MM"),
	DATE(3, "yyyy-MM-dd"),
	TIME(4, "HH:mm:ss"),
	DATETIME(5, "yyyy-MM-dd HH:mm:ss")
	;
	
	private int value;
	
	private String text;
	
	private DateTimeConfigTypeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	/**
	 * @Title: getEnumByValue
	 * @Description: 根据value值，获取枚举对象
	 * @param value
	 * @return
	 */
	public static DateTimeConfigTypeEnum getEnumByValue(Integer value) {
		if (value == null) {
			return null;
		}
		
		for (DateTimeConfigTypeEnum e : values()) {
			if (e.getValue() == value.intValue()) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * @Title: getTextByValue
	 * @Description: 根据value，获取text
	 * @param value
	 * @return
	 */
	public static String getTextByValue(Integer value) {
		DateTimeConfigTypeEnum e = getEnumByValue(value);
		if (e == null) {
			return StringUtils.EMPTY;
		}
		return e.getText();
	}
	
	/**
	 * @Title: toJson
	 * @Description: 返回json数据
	 * @return
	 */
	public static Collection<ValueTextBean> toJson() {
		Collection<ValueTextBean> list = new ArrayList<>();
		for (DateTimeConfigTypeEnum e : values()) {
			list.add(new ValueTextBean(String.valueOf(e.getValue()), e.getText()));
		}
		return list;
	}

	public String getText() {
		return text;
	}

	public int getValue() {
		return value;
	}
}
