package com.hp.springboot.admin.service;

import org.apache.commons.collections4.MultiValuedMap;

/**
 * 描述：用户与角色关系的service
 * 作者：黄平
 * 时间：2021年1月12日
 */
public interface ISysUserRoleService {

	/**
	 * @Title: queryAllUserRole
	 * @Description: 获取所有的用户与角色之间关系
	 * key=userId，value=对应的角色id列表
	 * @return
	 */
	MultiValuedMap<Integer, Integer> queryAllUserRole();
}
