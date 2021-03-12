package com.hp.springboot.admin.enums;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.hp.springboot.common.bean.ValueTextBean;

/**
 * 描述：配置类型
 * 作者：黄平
 * 时间：2021年2月24日
 */
public enum ConfigValueTypeEnum {

	TEXT(1, "文本"),
	NUMBER(2, "数字"),
	DATE_TIME(3, "日期时间"),
	//RICH_TEXT(4, "富文本")
	;
	
	private int value;
	
	private String text;
	
	private ConfigValueTypeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	/**
	 * @Title: getEnumByValue
	 * @Description: 根据value值，获取枚举对象
	 * @param value
	 * @return
	 */
	public static ConfigValueTypeEnum getEnumByValue(Integer value) {
		if (value == null) {
			return null;
		}
		
		for (ConfigValueTypeEnum e : values()) {
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
		ConfigValueTypeEnum e = getEnumByValue(value);
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
		for (ConfigValueTypeEnum e : values()) {
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
