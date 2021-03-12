package com.hp.springboot.admin.service;

import java.util.List;

import com.hp.springboot.common.bean.ValueTextBean;

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
	
	/**
	 * @Title: selectUserIdByRoleId
	 * @Description: 根据角色id，查询关联的用户id
	 * @param roleId
	 * @return
	 */
	List<Integer> selectUserIdByRoleId(Integer roleId);
	
	/**
	 * @Title: queryUserRoleByUserId
	 * @Description: 根据用户id，查询所有角色，并且查询已经分配的角色
	 * @param userId
	 * @return
	 */
	List<ValueTextBean> queryUserRoleByUserId(Integer userId);
	
	/**
	 * @Title: deleteByUserId
	 * @Description: 删除该用户与角色关联关系
	 * @param userId
	 */
	void deleteByUserId(Integer userId);
}
