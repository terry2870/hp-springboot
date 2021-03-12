package com.hp.springboot.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.springboot.admin.dal.ISysRoleMenuDAO;
import com.hp.springboot.admin.dal.model.SysRoleMenu;
import com.hp.springboot.admin.service.ISysRoleMenuService;
import com.hp.springboot.database.annotation.UseDatabase;
import com.hp.springboot.database.bean.SQLBuilders;
import com.hp.springboot.database.bean.SQLWhere;

/**
 * 描述：角色与菜单关系service
 * 作者：黄平
 * 时间：2021年2月7日
 */
@Service
public class SysRoleMenuServiceImpl implements ISysRoleMenuService {

	private static Logger log = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);
	
	@Autowired
	private ISysRoleMenuDAO sysRoleMenuDAO;
	
	@Override
	public List<Integer> queryRoleIdFromMenuId(Integer menuId) {
		log.info("queryRoleIdFromMenuId with menuId={}", menuId);
		return sysRoleMenuDAO.selectAnyList(SQLBuilders.create()
				.withSelect("role_id")
				.withWhere(SQLWhere.builder()
						.eq("menu_id", menuId)
						.build()
						)
				, Integer.class);
	}

	@Override
	public void deleteByRoleId(Integer roleId) {
		log.info("deleteByRoleId with roleId={}", roleId);
		sysRoleMenuDAO.deleteByBuilder(SQLWhere.builder()
				.eq("role_id", roleId)
				.build());
	}

	@Override
	public List<Integer> queryMenuByRoleId(Integer roleId) {
		log.info("queryMenuByRoleId with roleId={}", roleId);
		List<Integer> list = sysRoleMenuDAO.selectAnyList(SQLBuilders.create()
				.withSelect("distinct menu_id")
				.withWhere(SQLWhere.builder()
						.eq("role_id", roleId)
						.build())
				, Integer.class);
		if (CollectionUtils.isEmpty(list)) {
			log.warn("queryMenuByRoleId result is empty. with roleId={}", roleId);
			return null;
		}
		
		return list;
	}

	@Override
	@UseDatabase("test")
	@Transactional
	public void saveSysRoleMenu(Integer roleId, String menuIds) {
		log.info("saveSysRoleMenu with roleId={}, menuIds={}", roleId, menuIds);
		
		//删除该角色以前的菜单
		sysRoleMenuDAO.deleteByBuilder(SQLWhere.builder()
				.eq("role_id", roleId)
				.build());
		
		//插入新菜单
		if (StringUtils.isEmpty(menuIds)) {
			log.warn("saveSysRoleMenu with new menuId is empty.");
			return;
		}
		String[] arr = menuIds.split(",");
		List<SysRoleMenu> list = new ArrayList<>(arr.length);
		for (String menuId : arr) {
			list.add(new SysRoleMenu(roleId, Integer.parseInt(menuId)));
		}
		sysRoleMenuDAO.insertBatch(list);
	}


}
