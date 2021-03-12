package com.hp.springboot.admin.enums;

import java.util.ArrayList;
import java.util.Collection;

import com.hp.springboot.common.bean.ValueTextBean;

/**
 * 菜单类型枚举
 * @author hp
 * @date 2014-03-11
 */
public enum MenuTypeEnum {

	/**
	 * 根节点
	 */
	ROOT(1, "根节点"),

	/**
	 * 子菜单
	 */
	CHILD(2, "子节点"),

	
	/**
	 * 按钮
	 */
	BUTTON(3, "按钮");

	private MenuTypeEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	private int value;
	private String name;

	public int getValue() {
		return value;
	}
	
	public Integer getIntegerValue() {
		return Integer.valueOf(value);
	}

	public String getName() {
		return name;
	}
	
	/**
	 * @Title: toJson
	 * @Description: 返回json格式的数据
	 * @return
	 */
	public static Collection<ValueTextBean> toJson() {
		Collection<ValueTextBean> list = new ArrayList<>();
		for (MenuTypeEnum e : values()) {
			list.add(new ValueTextBean(String.valueOf(e.getValue()), e.getName()));
		}
		return list;
	}
}

