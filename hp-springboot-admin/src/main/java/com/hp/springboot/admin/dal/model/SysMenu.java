package com.hp.springboot.admin.dal.model;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 描述：菜单的对象
 * 作者：黄平
 * 时间：2021年1月13日
 */
public class SysMenu extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4028694432299713230L;

	@Id
	private Integer id;
	
	/**
	 * 菜单名称
	 */
	private String menuName;
	
	/**
	 * 菜单地址
	 */
	private String menuUrl;
	
	/**
	 * 父菜单ID
	 */
	private Integer parentMenuId;
	
	/**
	 * 排序号
	 */
	private Integer sortNumber;
	
	/**
	 * 菜单类型（1-根节点；2-子结点；3-按钮）
	 */
	private Integer menuType;
	
	/**
	 * 按钮的ID名称
	 */
	private String buttonId;
	
	/**
	 * 菜单图标
	 */
	private String iconName;
	
	/**
	 * 菜单指向
	 */
	private String target;
	
	/**
	 * 额外参数
	 */
	private String extraUrl;

	/**
	 * 访问该菜单，需要的角色
	 */
	@Transient
	private List<Integer> roleIdList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getButtonId() {
		return buttonId;
	}

	public void setButtonId(String buttonId) {
		this.buttonId = buttonId;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getExtraUrl() {
		return extraUrl;
	}

	public void setExtraUrl(String extraUrl) {
		this.extraUrl = extraUrl;
	}

	public List<Integer> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<Integer> roleIdList) {
		this.roleIdList = roleIdList;
	}
}
