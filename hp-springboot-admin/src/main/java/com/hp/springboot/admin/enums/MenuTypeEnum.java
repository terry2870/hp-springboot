package com.hp.springboot.admin.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
	public static Collection<?> toJson() {
		Collection<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = null;
		for (MenuTypeEnum e : values()) {
			map = new HashMap<>();
			map.put("value", String.valueOf(e.getValue()));
			map.put("text", e.toString());
			list.add(map);
		}
		return list;
	}
}

