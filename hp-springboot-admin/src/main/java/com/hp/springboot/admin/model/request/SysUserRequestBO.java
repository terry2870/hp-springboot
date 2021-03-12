package com.hp.springboot.admin.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hp.springboot.common.bean.BaseRequestBO;

/**
 * @author huangping
 * 2018-08-06
 */
public class SysUserRequestBO extends BaseRequestBO {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	private String nickName;

	/**
	 * 登录名
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
	 * 固定电话
	 */
	private String phoneNumber;
	
	/**
	 * 用户头像
	 */
	private String headIcon;

	/**
	 * 用户地址
	 */
	private String address;

	/**
	 * email
	 */
	private String email;

	/**
	 * 最后一次登录时间
	 */
	private Integer lastLoginTime;

	/**
	 * 用户身份（1-超级管理员；2-店长；3-店员）
	 */
	private Integer identity;
	
	private String checkCode;
	
	private List<Integer> roleIdList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Integer lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public List<Integer> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Integer> roleIdList) {
		this.roleIdList = roleIdList;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

}
