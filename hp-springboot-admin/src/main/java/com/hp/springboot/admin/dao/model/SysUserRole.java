package com.hp.springboot.admin.dao.model;

import javax.persistence.Id;

import com.hp.springboot.common.bean.AbstractBean;

/**
 * 描述：用户角色对应关系对象
 * 作者：黄平
 * 时间：2021年1月12日
 */
public class SysUserRole extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4364444846138491885L;

	@Id
	private Integer id;
	
	/**
	 * 用户id
	 */
	private Integer userId;
	
	/**
	 * 角色id
	 */
	private Integer roleId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
