package com.hp.springboot.common.enums;

import java.util.ArrayList;
import java.util.Collection;

import com.hp.springboot.common.bean.ValueTextBean;

/**
 * 描述：状态枚举
 * 作者：黄平
 * 时间：2021年1月25日
 */
public enum StatusEnum {

	VALID(1, "有效"),
	INVALID(0, "无效");
	
	private int value;
	private String text;
	
	private StatusEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	/**
	 * @Title: toJson
	 * @Description: 返回json格式的数据
	 * @return
	 */
	public static Collection<ValueTextBean> toJson() {
		Collection<ValueTextBean> list = new ArrayList<>();
		for (StatusEnum e : values()) {
			list.add(new ValueTextBean(String.valueOf(e.getValue()), e.getText()));
		}
		return list;
	}
	
	/**
	 * @Title: getEnumByValue
	 * @Description: 根据value值，获取枚举对象
	 * @param value
	 * @return
	 */
	public static StatusEnum getEnumByValue(Integer value) {
		if (value == null) {
			return null;
		}
		for (StatusEnum e : values()) {
			if (value.intValue() == e.getValue()) {
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
		StatusEnum e = getEnumByValue(value);
		return e == null ? "" : e.getText();
	}

	public Integer getIntegerValue() {
		return Integer.valueOf(value);
	}
	
	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}
}
