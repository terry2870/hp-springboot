package com.hp.springboot.admin.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hp.springboot.common.bean.AbstractBean;

/**
 * 描述：登录请求对象
 * 作者：黄平
 * 时间：2021年2月7日
 */
public class LoginRequestBO extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1401415523073591461L;

	private String username;
	
	@JsonIgnore
	private String password;
	
	private String checkcode;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckcode() {
		return checkcode;
	}

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
}
