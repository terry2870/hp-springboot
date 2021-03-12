package com.hp.springboot.admin.model.response;

import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.hp.springboot.common.bean.BaseResponseBO;

/**
 * 描述：
 * 作者：黄平
 * 时间：2021年1月19日
 */
public class SysUserResponseBO extends BaseResponseBO implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6526439238397959624L;

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
	 * 固定电话
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
	
	/**
	 * 权限
	 */
	@JsonIgnore
	private Collection<GrantedAuthority> authorities;
	
	public SysUserResponseBO() {}
	
	public SysUserResponseBO(Integer id, String loginName, String loginPwd, String nickName, GrantedAuthority... authorities) {
		this.id = id;
		this.loginName = loginName;
		this.loginPwd = loginPwd;
		this.nickName = nickName;
		this.setStatus(NumberUtils.INTEGER_ONE);
		
		if (ArrayUtils.isEmpty(authorities)) {
			return;
		}
		this.authorities = Lists.newArrayList(authorities);
	}

	@Override
	public String getPassword() {
		return loginPwd;
	}

	@Override
	public String getUsername() {
		return loginName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return BooleanUtils.toBoolean(this.getStatus(), NumberUtils.INTEGER_ONE, NumberUtils.INTEGER_ZERO);
	}

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

	/**
	 * 获取该用户的角色
	 */
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public String getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(String headIcon) {
		this.headIcon = headIcon;
	}

}
