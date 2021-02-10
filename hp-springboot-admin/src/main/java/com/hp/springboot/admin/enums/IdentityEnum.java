/**
 * 
 */
package com.hp.springboot.admin.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangping
 * 2016年8月30日 上午12:17:41
 */
public enum IdentityEnum {
	
	
	SUPERUSER(1, "超级管理员"),
	MANAGER(2, "普通管理员"),
	NORMAL(3, "普通员工");
	

	private IdentityEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	private int value;
	private String name;

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	/**
	 * 检查是不是超级管理员
	 * @param identity
	 * @return
	 */
	public static boolean checkIsSuperUser(Integer identity) {
		if (identity == null) {
			return false;
		}
		return identity.intValue() == SUPERUSER.getValue();
	}
	
	/**
	 * 检查是不是普通管理员
	 * @param identity
	 * @return
	 */
	public static boolean checkIsManager(Integer identity) {
		if (identity == null) {
			return false;
		}
		return identity.intValue() == MANAGER.getValue();
	}
	
	/**
	 * 检查是不是普通员工
	 * @param identity
	 * @return
	 */
	public static boolean checkIsNormalUser(Integer identity) {
		if (identity == null) {
			return false;
		}
		return identity.intValue() == NORMAL.getValue();
	}
	
	/**
	 * @Title: toJson
	 * @Description: 返回json格式的数据
	 * @return
	 */
	public static Collection<?> toJson() {
		Collection<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map = null;
		for (IdentityEnum e : values()) {
			map = new HashMap<>();
			map.put("value", String.valueOf(e.getValue()));
			map.put("text", e.toString());
			list.add(map);
		}
		return list;
	}
	
	public static String getNameByValue(Integer value) {
		if (value == null) {
			return null;
		}
		for (IdentityEnum e : values()) {
			if (value == e.getValue()) {
				return e.getName();
			}
		}
		return null;
	}
}
