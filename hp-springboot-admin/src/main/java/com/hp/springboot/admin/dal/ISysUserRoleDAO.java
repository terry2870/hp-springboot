package com.hp.springboot.admin.dal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hp.springboot.admin.dal.model.SysUserRole;
import com.hp.springboot.common.bean.ValueTextBean;
import com.hp.springboot.mybatis.mapper.BaseMapper;

/**
 * 描述：用户角色关系dao
 * 作者：黄平
 * 时间：2021年1月12日
 */
public interface ISysUserRoleDAO extends BaseMapper<SysUserRole, Integer> {

	/**
	 * d
	 * @Title: selectUserRoleByUserId
	 * @Description: 查询角色，并且根据用户id，查询已经分配给该用户的角色
	 * @param userId
	 * @return
	 */
	List<ValueTextBean> selectUserRoleByUserId(@Param("userId") Integer userId);
}
