package com.hp.springboot.admin.dal.model;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 描述：用户实体类
 * 作者：黄平
 * 时间：2021年1月12日
 */
public class SysUser extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8782352285927001855L;
	
	
	@Id
	private Integer id;
	
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 登录名称
	 */
	private String loginName;
	
	/**
	 * 登录密码
	 */
	@JsonIgnore
	private String loginPwd;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 固定电弧
	 */
	private String phoneNumber;
	
	/**
	 * 用户头像
	 */
	private String headIcon;
	
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * 用户身份
	 */
	private Integer identity;
	
	/**
	 * 最后登录时间
	 */
	private Integer lastLoginTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public Integer getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Integer lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

}
