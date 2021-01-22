package com.hp.springboot.admin.service;

import org.apache.commons.collections4.MultiValuedMap;

/**
 * 描述：菜单的service
 * 作者：黄平
 * 时间：2021年1月13日
 */
public interface ISysMenuService {

	/**
	 * @Title: queryAllRoleMenu
	 * @Description: 查询所有菜单需要的角色权限，就是访问该菜单需要的角色
	 * key=菜单url，value就是角色Id列表
	 * @return
	 */
	MultiValuedMap<String, String> queryAllMenuRoles();
}
