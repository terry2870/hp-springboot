package com.hp.springboot.admin.dal.model;

import javax.persistence.Id;

/**
 * @author huangping
 * 2018-08-06
 */
public class SysRole extends BaseModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@Id
	private Integer id;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String roleInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleInfo() {
		return roleInfo;
	}

	public void setRoleInfo(String roleInfo) {
		this.roleInfo = roleInfo;
	}

}
