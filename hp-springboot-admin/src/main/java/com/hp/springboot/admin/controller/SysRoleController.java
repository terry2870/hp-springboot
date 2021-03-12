package com.hp.springboot.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.model.request.SysRoleRequestBO;
import com.hp.springboot.admin.model.response.SysRoleResponseBO;
import com.hp.springboot.admin.service.ISysRoleMenuService;
import com.hp.springboot.admin.service.ISysRoleService;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.database.bean.PageRequest;
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
	
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysRoleMenuService sysRoleMenuService;
	
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
	
	/**
	 * 查询系统角色表列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/querySysRolePageList")
	@ResponseBody
	public Response<PageResponse<SysRoleResponseBO>> querySysRolePageList(SysRoleRequestBO request, PageRequest pageRequest) {
		log.info("querySysRolePageList with request={}, page={}", request, pageRequest);
		PageResponse<SysRoleResponseBO> list = sysRoleService.querySysRolePageList(request, pageRequest);
		log.info("querySysRolePageList success. with request={}, page={}", request, pageRequest);
		if (list == null) {
			return new Response<>(new PageResponse<>());
		}
		return Response.success(list);
	}

	/**
	 * 保存系统角色表
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysRole")
	@ResponseBody
	public Response<Object> saveSysRole(SysRoleRequestBO request) {
		log.info("saveSysRole with request={}", request);
		sysRoleService.saveSysRole(request);
		log.info("saveSysRole success. with request={}", request);
		return Response.success();
	}

	/**
	 * 删除系统角色表
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSysRole")
	@ResponseBody
	public Response<Object> deleteSysRole(Integer id) {
		log.info("deleteSysRole with id={}", id);
		sysRoleService.deleteSysRole(id);
		log.info("deleteSysRole success. with id={}", id);
		return Response.success();
	}

	/**
	 * 根据id，查询系统角色表
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySysRoleById")
	@ResponseBody
	public Response<SysRoleResponseBO> querySysRoleById(Integer id) {
		log.info("querySysRoleById with id={}", id);
		SysRoleResponseBO bo = sysRoleService.querySysRoleById(id);
		log.info("querySysRoleById success. with id={}", id);
		return Response.success(bo);
	}
	
	/**
	 * 查询该角色的菜单
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/queryMenuByRoleId")
	@ResponseBody
	public Response<List<Integer>> queryMenuByRoleId(Integer roleId) {
		log.info("queryMenuByRoleId with roleId={}", roleId);
		List<Integer> list = sysRoleMenuService.queryMenuByRoleId(roleId);
		return Response.success(list);
	}
	
	/**
	 * 保存角色菜单
	 * @param roleId
	 * @param menuIds
	 */
	@RequestMapping("/saveSysRoleMenu")
	@ResponseBody
	public Response<Object> saveSysRoleMenu(Integer roleId, String menuIds) {
		log.info("saveSysRoleMenu with roleId={}, menuIds={}", roleId, menuIds);
		sysRoleMenuService.saveSysRoleMenu(roleId, menuIds);
		return Response.success();
	}
}
