package com.hp.springboot.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.dao.ISysMenuDAO;
import com.hp.springboot.admin.dao.ISysRoleMenuDAO;
import com.hp.springboot.admin.dao.model.SysMenu;
import com.hp.springboot.admin.dao.model.SysRoleMenu;
import com.hp.springboot.admin.service.ISysMenuService;
import com.hp.springboot.common.constant.GoogleContant;
import com.hp.springboot.database.bean.SQLBuilders;

/**
 * 描述：菜单的service实现
 * 作者：黄平
 * 时间：2021年1月13日
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

	private static Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);
	
	@Autowired
	private ISysRoleMenuDAO sysRoleMenuDAO;
	@Autowired
	private ISysMenuDAO sysMenuDAO;
	
	@Override
	public synchronized MultiValuedMap<String, String> queryAllMenuRoles() {
		log.info("start to queryAllMenuRoles.");
		if (AdminConstants.MENU_ROLES_MAP != null) {
			// 首先从缓存中获取
			return AdminConstants.MENU_ROLES_MAP;
		}
		
		MultiValuedMap<String, String> resp = new ArrayListValuedHashMap<>();
		
		// 缓存没有，则查询数据库
		List<SysRoleMenu> roleMenuList = sysRoleMenuDAO.selectList(SQLBuilders.create());
		if (CollectionUtils.isNotEmpty(roleMenuList)) {
			Set<Integer> menuIdSet = new HashSet<>();
			// 遍历，获取所有menuId
			for (SysRoleMenu sysRoleMenu : roleMenuList) {
				menuIdSet.add(sysRoleMenu.getMenuId());
			}
			
			// 查询这些菜单详细信息
			List<SysMenu> menuList = sysMenuDAO.selectByPrimaryKeys(new ArrayList<>(menuIdSet));
			if (CollectionUtils.isNotEmpty(menuList)) {
				// list转成map
				Map<Integer, SysMenu> menuMap = new HashMap<>();
				MapUtils.populateMap(menuMap, menuList, SysMenu::getId);
				
				// 遍历roleMenulList
				SysMenu menu = null;
				List<String> extraUrlList = null;
				for (SysRoleMenu sysRoleMenu : roleMenuList) {
					menu = menuMap.get(sysRoleMenu.getMenuId());
					if (menu == null) {
						continue;
					}
					// 以menuUrl为key，roleId为value
					resp.put(menu.getMenuUrl(), sysRoleMenu.getRoleId().toString());
					
					// 再遍历extraUrl字段，也一起设置进去
					if (StringUtils.isEmpty(menu.getExtraUrl())) {
						continue;
					}
					
					extraUrlList = GoogleContant.COMMA_SPLITTER.splitToList(menu.getExtraUrl());
					if (CollectionUtils.isEmpty(extraUrlList)) {
						continue;
					}
					
					// 放入map
					for (String str : extraUrlList) {
						resp.put(str, sysRoleMenu.getRoleId().toString());
					}
				}
			}
		}
		
		// 写入缓存
		AdminConstants.MENU_ROLES_MAP = resp;
		return resp;
	}

}
