package com.hp.springboot.admin.dal.model;

import javax.persistence.Id;

import com.hp.springboot.common.bean.AbstractBean;

/**
 * 描述：角色与菜单关系
 * 作者：黄平
 * 时间：2021年1月13日
 */
public class SysRoleMenu extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4316442848292079501L;

	@Id
	private Integer id;
	
	/**
	 * 角色id
	 */
	private Integer roleId;
	
	/**
	 * 菜单id
	 */
	private Integer menuId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
}
