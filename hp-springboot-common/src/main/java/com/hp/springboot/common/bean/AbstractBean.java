package com.hp.springboot.common.bean;

import java.io.Serializable;

import com.hp.springboot.common.util.JacksonUtil;

/**
 * 描述：所有bean对象的父类
 * 作者：20025894
 * 时间：2020-12-23
 */
public abstract class AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6918513170626340942L;

	@Override
	public String toString() {
		return JacksonUtil.toJSONString(this);
	}
}
