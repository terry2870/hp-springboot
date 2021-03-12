package com.hp.springboot.common.bean;

/**
 * 描述：
 * 作者：黄平
 * 时间：2021年2月20日
 */
public class KeyValueBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4534801697174270083L;

	private String key;
	
	private Object value;

	public KeyValueBean() {}
	
	public KeyValueBean(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
