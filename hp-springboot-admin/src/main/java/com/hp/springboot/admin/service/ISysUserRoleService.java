package com.hp.springboot.admin.service;

import java.util.List;

/**
 * 描述：用户与角色关系的service
 * 作者：黄平
 * 时间：2021年1月12日
 */
public interface ISysUserRoleService {
	
	/**
	 * @Title: insertUserRole
	 * @Description: 插入用户的角色关系
	 * @param userId
	 * @param roleIdList
	 */
	void insertUserRole(Integer userId, List<Integer> roleIdList);
}
