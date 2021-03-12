package com.hp.springboot.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.springboot.admin.dal.ISysUserRoleDAO;
import com.hp.springboot.admin.dal.model.SysUserRole;
import com.hp.springboot.admin.service.ISysUserRoleService;
import com.hp.springboot.common.bean.ValueTextBean;
import com.hp.springboot.database.bean.SQLBuilders;
import com.hp.springboot.database.bean.SQLWhere;

/**
 * 描述：用户与角色关系实现类
 * 作者：黄平
 * 时间：2021年1月12日
 */
@Service
public class SysUserRoleServiceImpl implements ISysUserRoleService {

	private static Logger log = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);
	
	@Autowired
	private ISysUserRoleDAO sysUserRoleDAO;

	@Override
	public void insertUserRole(Integer userId, List<Integer> roleIdList) {
		log.info("insertUserRole with userId={}, roleIdList={}", userId, roleIdList);
		
		//首先删除该用户关联的角色
		sysUserRoleDAO.deleteByBuilder(SQLWhere.builder()
				.eq("user_id", userId)
				.build());
		
		if (CollectionUtils.isEmpty(roleIdList)) {
			return;
		}
		List<SysUserRole> list = new ArrayList<>(roleIdList.size());
		for (Integer roleId : roleIdList) {
			list.add(new SysUserRole(userId, roleId));
		}
		
		// 批量插入
		sysUserRoleDAO.insertBatch(list);
	}

	@Override
	public List<Integer> selectUserIdByRoleId(Integer roleId) {
		log.info("selectUserIdByRoleId with roleId={}", roleId);
		return sysUserRoleDAO.selectAnyList(SQLBuilders.create()
				.withSelect("user_id")
				.withWhere(SQLWhere.builder()
						.eq("role_id", roleId)
						.build())
				, Integer.class);
	}

	@Override
	public List<ValueTextBean> queryUserRoleByUserId(Integer userId) {
		log.info("queryUserRoleByUserId with userId={}", userId);
		return sysUserRoleDAO.selectUserRoleByUserId(userId);
	}

	@Override
	public void deleteByUserId(Integer userId) {
		log.info("deleteByUserId with userId={}", userId);
		sysUserRoleDAO.deleteByBuilder(SQLWhere.builder()
				.eq("user_id", userId)
				.build());
	}

}
