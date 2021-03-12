package com.hp.springboot.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.springboot.admin.convert.BaseConvert;
import com.hp.springboot.admin.convert.SysMenuConvert;
import com.hp.springboot.admin.dal.ISysMenuDAO;
import com.hp.springboot.admin.dal.model.SysMenu;
import com.hp.springboot.admin.model.request.SysMenuRequestBO;
import com.hp.springboot.admin.model.response.SysMenuResponseBO;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.admin.service.ISysMenuService;
import com.hp.springboot.admin.service.ISysRoleMenuService;
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.constant.GoogleContant;
import com.hp.springboot.common.enums.StatusEnum;
import com.hp.springboot.common.exception.CommonException;
import com.hp.springboot.common.util.DateUtil;
import com.hp.springboot.common.util.NumberUtil;
import com.hp.springboot.database.bean.OrderBy;
import com.hp.springboot.database.bean.SQLBuilders;
import com.hp.springboot.database.bean.SQLWhere;

/**
 * 描述：菜单的service实现
 * 作者：黄平
 * 时间：2021年1月13日
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

	private static Logger log = LoggerFactory.getLogger(SysMenuServiceImpl.class);
	
	@Autowired
	private ISysMenuDAO sysMenuDAO;
	@Autowired
	private ISysRoleMenuService sysRoleMenuService;

	@Override
	public List<SysMenuResponseBO> queryUserSysMenu(StatusEnum status) {
		log.info("queryUserSysMenu start. with status={}", status);
		
		List<SysMenu> menuList = null;
		//查询当前用户的菜单
		if (SecuritySessionUtil.isAdmin()) {
			// admin用户
			menuList = sysMenuDAO.selectList(SQLBuilders.create()
					.withWhere(SQLWhere.builder()
							.eq("status", status == null ? null : status.getValue())
							.build())
					.withOrder(OrderBy.of("parent_menu_id"), OrderBy.of("sort_number"))
					);
		} else {
			SysUserResponseBO user = SecuritySessionUtil.getSessionData();
			// 其他用户
			// 查询该用户分配的角色所对应的菜单id
			List<Integer> menuIdList = sysMenuDAO.selectByUserId(user.getId());
			if (CollectionUtils.isEmpty(menuIdList)) {
				return null;
			}
			
			// 向上递归
			menuList = sysMenuDAO.selectSysMenuByLeafMenuIds(GoogleContant.COMMA_JOINER.join(menuIdList));
		}
		
		if (CollectionUtils.isEmpty(menuList)) {
			return null;
		}
		
		List<SysMenuResponseBO> respList = new ArrayList<>(menuList.size());
		for (SysMenu menu : menuList) {
			respList.add(SysMenuConvert.dal2BOResponse(menu));
		}
		return respList;
	}
	
	@Override
	public void saveSysMenu(SysMenuRequestBO request) {
		log.info("saveSysMenu with request={}", request);
		SysMenu dal = SysMenuConvert.boRequest2Dal(request);
		if (NumberUtil.isNullOrZero(request.getId())) {
			//新增
			BaseConvert.convertModel(dal);
			sysMenuDAO.insertSelective(dal);
		} else {
			//修改
			dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			sysMenuDAO.updateByPrimaryKeySelective(dal);
		}
	}

	@Override
	public void deleteSysMenu(Integer id, boolean force) {
		log.info("deleteSysMenu with id={}, force={}", id, force);
		
		// 检查是否有子节点
		int childCount = sysMenuDAO.selectCount(SQLWhere.builder()
				.eq("parent_menu_id", id)
				.build()
				);
		if (childCount > 0) {
			log.warn("deleteSysMenu error. with child find bind. with menuId={}, force={}", id, force);
			throw new CommonException(500, "该菜单下有子菜单，不能删除。请先删除子菜单，再删除该菜单。");
		}
		
		// 检查该菜单有没有角色在用
		List<Integer> roleIdList = sysRoleMenuService.queryRoleIdFromMenuId(id);
		if (CollectionUtils.isNotEmpty(roleIdList)) {
			log.warn("deleteSysMenu error. with role bind. with menuId={}, force={}", id, force);
			throw new CommonException(500, "该菜单有角色关联，不能删除。请先解除角色与菜单关联关系，再删除该菜单。");
		}
		
		if (force) {
			//物理删除
			sysMenuDAO.deleteByPrimaryKey(id);
		} else {
			// 逻辑删除
			SysMenu menu = new SysMenu();
			menu.setId(id);
			menu.setStatus(StatusEnum.INVALID.getValue());
			menu.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			sysMenuDAO.updateByPrimaryKeySelective(menu);
		}
	}

}
