package com.hp.springboot.admin.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hp.springboot.admin.dal.model.SysMenu;
import com.hp.springboot.mybatis.mapper.BaseMapper;

/**
 * 描述：菜单的操作dao
 * 作者：黄平
 * 时间：2021年1月13日
 */
public interface ISysMenuDAO extends BaseMapper<SysMenu, Integer> {

	/**
	 * 查询该用户的菜单（非超级管理员）
	 * @param userId
	 * @return
	 */
	List<Integer> selectByUserId(@Param("userId") Integer userId);
}
