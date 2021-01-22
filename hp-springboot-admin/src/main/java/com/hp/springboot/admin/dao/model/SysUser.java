package com.hp.springboot.admin.dao.model;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hp.springboot.common.bean.AbstractBean;

/**
 * 描述：用户实体类
 * 作者：黄平
 * 时间：2021年1月12日
 */
public class SysUser extends AbstractBean {

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
	 * 地址
	 */
	private String address;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 创建者id
	 */
	private Integer createUserId;
	
	/**
	 * 创建时间
	 */
	private Integer createTime;
	
	/**
	 * 更新时间
	 */
	private Integer updateTime;
	
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Integer updateTime) {
		this.updateTime = updateTime;
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

}
