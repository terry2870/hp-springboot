package com.hp.springboot.admin.service;

import java.util.List;

import com.hp.springboot.admin.model.request.SysMenuRequestBO;
import com.hp.springboot.admin.model.response.SysMenuResponseBO;
import com.hp.springboot.common.enums.StatusEnum;

/**
 * 描述：菜单的service
 * 作者：黄平
 * 时间：2021年1月13日
 */
public interface ISysMenuService {
	
	/**
	 * @Title: queryUserSysMenu
	 * @Description: 查询用户可以看到的菜单
	 * @param status
	 * @return
	 */
	List<SysMenuResponseBO> queryUserSysMenu(StatusEnum status);
	
	/**
	 * @Title: saveSysMenu
	 * @Description: 保存系统菜单表
	 * @param request
	 */
	void saveSysMenu(SysMenuRequestBO request);

	/**
	 * @Title: deleteSysMenu
	 * @Description: 删除系统菜单表
	 * @param id
	 */
	void deleteSysMenu(Integer id);
}
