package com.hp.springboot.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.model.request.SysMenuRequestBO;
import com.hp.springboot.admin.model.response.SysMenuResponseBO;
import com.hp.springboot.admin.service.ISysMenuService;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.enums.StatusEnum;
import com.hp.springboot.freemarker.util.FreeMarkerUtil;

/**
 * 描述：菜单的控制器
 * 作者：黄平
 * 时间：2021年2月9日
 */
@RestController
@RequestMapping("/SysMenuController")
public class SysMenuController {

	private static Logger log = LoggerFactory.getLogger(SysMenuController.class);
	
	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * @Title: sysMenuList
	 * @Description: 菜单首页
	 * 只允许admin角色访问
	 * @return
	 */
	@PreAuthorize("hasAnyRole('"+ AdminConstants.ADMIN_ROLE_NAME +"')")
	@RequestMapping("/sysMenuList")
	public ModelAndView sysMenuList() {
		Map<String, Object> map = new HashMap<>();
		FreeMarkerUtil.addStaticTemplate(map);
		map.put("showPage", "sysManage/sysMenu/sysMenuList");
		return new ModelAndView("/default", map);
	}
	
	/**
	 * @Title: queryAllSysMenu
	 * @Description: 查询当前登录用户所能看到的菜单
	 * @param status
	 * @return
	 */
	@RequestMapping("/queryAllSysMenu")
	public Response<List<SysMenuResponseBO>> queryAllSysMenu(Integer status) {
		log.info("enter queryAllSysMenu start");
		//这里查询所有菜单，包含已经删除的菜单
		List<SysMenuResponseBO> list = sysMenuService.queryUserSysMenu(StatusEnum.getEnumByValue(status));
		if (list == null) {
			return Response.emptyList();
		}
		return Response.success(list);
	}

	/**
	 * @Title: saveSysMenu
	 * @Description: 保存系统菜单表
	 * @param request
	 * @return
	 */
	@PreAuthorize("hasAnyRole('"+ AdminConstants.ADMIN_ROLE_NAME +"')")
	@RequestMapping("/saveSysMenu")
	public Response<Object> saveSysMenu(SysMenuRequestBO request) {
		log.info("enter saveSysMenu with request={}", request);
		sysMenuService.saveSysMenu(request);
		return Response.success();
	}

	/**
	 * @Title: deleteSysMenu
	 * @Description: 删除系统菜单表
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyRole('"+ AdminConstants.ADMIN_ROLE_NAME +"')")
	@RequestMapping("/deleteSysMenu")
	public Response<Object> deleteSysMenu(Integer id, boolean force) {
		log.info("enter deleteSysMenu with id={}, force={}", id, force);
		sysMenuService.deleteSysMenu(id, force);
		log.info("deleteSysMenu success. with id={}, force={}", id, force);
		return Response.success();
	}
}
