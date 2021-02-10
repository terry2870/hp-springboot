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
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.enums.StatusEnum;
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
			List<Integer> menuIdList = sysMenuDAO.selectByUserId(user.getId());
			if (CollectionUtils.isEmpty(menuIdList)) {
				return null;
			}
			
			// 根据id，批量查询菜单信息
			menuList = sysMenuDAO.selectByPrimaryKeys(menuIdList);
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
		if (NumberUtil.isEmpty(request.getId())) {
			//新增
			BaseConvert.convertModel(dal);
			sysMenuDAO.insertSelective(dal);
		} else {
			//修改
			dal.setUpdateTime(DateUtil.getCurrentTimeSeconds());
			sysMenuDAO.updateByPrimaryKeySelective(dal);
		}
		log.info("saveSysMenu success with request={}", request);
	}

	@Override
	public void deleteSysMenu(Integer id) {
		log.info("deleteSysMenu with id={}", id);
		// 使用逻辑删除
		SysMenu menu = new SysMenu();
		menu.setId(id);
		menu.setStatus(StatusEnum.VALID.getValue());
		menu.setUpdateTime(DateUtil.getCurrentTimeSeconds());
		sysMenuDAO.updateByPrimaryKeySelective(menu);
		log.info("deleteSysMenu success with id={}", id);
	}

}
