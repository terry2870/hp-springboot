package com.hp.springboot.admin.service;

import java.util.List;

/**
 * 描述：角色与菜单关系service
 * 作者：黄平
 * 时间：2021年1月13日
 */
public interface ISysRoleMenuService {

	/**
	 * @Title: queryRoleIdFromMenuId
	 * @Description: 根据菜单id，查询关联的角色id
	 * @param menuId
	 * @return
	 */
	List<Integer> queryRoleIdFromMenuId(Integer menuId);
	
	/**
	 * @Title: deleteByRoleId
	 * @Description: 根据角色id，删除关联关系
	 * @param roleId
	 */
	void deleteByRoleId(Integer roleId);
	
	/**
	 * @Title: selectMenuByRoleId
	 * @Description: 根据角色ud，查询已经分配的菜单
	 * @param roleId
	 * @return
	 */
	List<Integer> queryMenuByRoleId(Integer roleId);
	
	/**
	 * @Title: saveSysRoleMenu
	 * @Description: 保存角色权限
	 * @param roleId
	 * @param menuIds
	 */
	void saveSysRoleMenu(Integer roleId, String menuIds);
}