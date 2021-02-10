package com.hp.springboot.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.model.request.SysUserRequestBO;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.admin.service.ISysUserService;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.database.bean.PageRequest;
import com.hp.springboot.freemarker.util.FreeMarkerUtil;

/**
 * 描述：用户管理的控制器
 * 作者：黄平
 * 时间：2021年1月27日
 */
@RestController
@RequestMapping("/SysUserController")
public class SysUserController {

	private static Logger log = LoggerFactory.getLogger(SysUserController.class);
	
	@Autowired
	private ISysUserService sysUserService;
	
	/**
	 * @Title: sysUserList
	 * @Description: 打开用户列表页面
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/sysUserList")
	public ModelAndView sysUserList(String menuId) {
		Map<String, Object> map = new HashMap<>();
		FreeMarkerUtil.addStaticTemplate(map);
		map.put("showPage", "sysManage/sysUser/sysUserList");
		return new ModelAndView("/default", map);
	}
	
	/**
	 * @Title: querySysUserPageList
	 * @Description: 查询用户列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/querySysUserPageList")
	public Response<PageResponse<SysUserResponseBO>> querySysUserPageList(SysUserRequestBO request, PageRequest pageRequest) {
		log.info("enter querySysUserPageList with request={}, pageRequest={}.", request, pageRequest);
		PageResponse<SysUserResponseBO> list = sysUserService.querySysUserPageList(request, pageRequest);
		return Response.success(list);
	}
	
	/**
	 * @Title: saveSysUser
	 * @Description: 保存用户
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysUser")
	public Response<Object> saveSysUser(SysUserRequestBO request) {
		log.info("enter saveSysUser with request={}.", request);
		sysUserService.saveSysUser(request);
		return Response.success();
	}
}
