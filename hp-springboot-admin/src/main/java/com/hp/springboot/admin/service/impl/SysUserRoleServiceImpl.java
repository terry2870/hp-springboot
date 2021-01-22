package com.hp.springboot.admin.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.dao.ISysUserRoleDAO;
import com.hp.springboot.admin.dao.model.SysUserRole;
import com.hp.springboot.admin.service.ISysUserRoleService;
import com.hp.springboot.database.bean.SQLBuilders;

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
	public synchronized MultiValuedMap<Integer, Integer> queryAllUserRole() {
		log.info("start to call queryAllUserRole.");
		
		if (AdminConstants.USER_ROLE_MAP != null) {
			// 首先从缓存中获取
			return AdminConstants.USER_ROLE_MAP;
		}
		
		MultiValuedMap<Integer, Integer> resp = new ArrayListValuedHashMap<>();
		
		// 缓存没有，则查询数据库
		List<SysUserRole> userRoleList = sysUserRoleDAO.selectList(SQLBuilders.create());
		if (CollectionUtils.isNotEmpty(userRoleList)) {
			for (SysUserRole sysUserRole : userRoleList) {
				resp.put(sysUserRole.getUserId(), sysUserRole.getRoleId());
			}
		}
		
		// 写入缓存
		AdminConstants.USER_ROLE_MAP = resp;
		return resp;
	}

}
