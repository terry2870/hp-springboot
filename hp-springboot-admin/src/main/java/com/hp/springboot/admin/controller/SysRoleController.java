package com.hp.springboot.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.freemarker.util.FreeMarkerUtil;

/**
 * 描述：角色控制器
 * 作者：黄平
 * 时间：2021年1月28日
 */
@RestController
@RequestMapping("/SysRoleController")
public class SysRoleController {

	private static Logger log = LoggerFactory.getLogger(SysRoleController.class);
	
	/**
	 * @Title: sysRoleList
	 * @Description: 打开角色列表页面
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/sysRoleList")
	public ModelAndView sysRoleList(String menuId) {
		Map<String, Object> map = new HashMap<>();
		FreeMarkerUtil.addStaticTemplate(map);
		map.put("showPage", "sysManage/sysRole/sysRoleList");
		return new ModelAndView("/default", map);
	}
}
