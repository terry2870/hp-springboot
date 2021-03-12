package com.hp.springboot.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.model.request.SysUserRequestBO;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.admin.service.ISysUserRoleService;
import com.hp.springboot.admin.service.ISysUserService;
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.bean.ValueTextBean;
import com.hp.springboot.common.enums.StatusEnum;
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
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
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
	 * @Title: sysUserEdit
	 * @Description: 打开用户编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/sysUserEdit")
	public ModelAndView sysUserEdit(Integer id) {
		Map<String, Object> map = new HashMap<>();
		
		// 查询该用户已经分配的角色
		List<ValueTextBean> roleList = sysUserRoleService.queryUserRoleByUserId(id);
		
		FreeMarkerUtil.addStaticTemplate(map);
		map.put("roleList", roleList);
		map.put("id", id);
		return new ModelAndView("sysManage/sysUser/sysUserEdit", map);
	}
	
	/**
	 * @Title: modifyPwdPage
	 * @Description: 打开修改密码页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/modifyPwdPage")
	public ModelAndView modifyPwdPage(Integer id) {
		Map<String, Object> map = new HashMap<>();		
		FreeMarkerUtil.addStaticTemplate(map);
		map.put("id", id);
		map.put("showPage", "sysManage/sysUser/modifyPwd");
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
	
	/**
	 * @Title: querySysUserById
	 * @Description: 根据id，查询用户详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySysUserById")
	public Response<SysUserResponseBO> querySysUserById(Integer id) {
		log.info("enter querySysUserById with id={}", id);
		SysUserResponseBO bo = sysUserService.querySysUserById(id);
		return Response.success(bo);
	}
	
	/**
	 * @Title: deleteSysUser
	 * @Description: 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSysUser")
	public Response<Object> deleteSysUser(Integer id) {
		log.info("enter deleteSysUser with id={}", id);
		sysUserService.deleteSysUser(id);
		return Response.success();
	}
	
	/**
	 * @Title: changeUserStatus
	 * @Description: 修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/changeUserStatus")
	public Response<Object> changeUserStatus(Integer id, Integer status) {
		log.info("enter changeUserStatus with id={}", id);
		sysUserService.changeUserStatus(id, StatusEnum.getEnumByValue(status));
		return Response.success();
	}
	
	/**
	 * @Title: modifyPwd
	 * @Description: 修改密码
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping("/modifyPwd")
	public Response<Object> modifyPwd(String oldPwd, String newPwd) {
		// session中获取用户id
		SysUserResponseBO user = SecuritySessionUtil.getSessionData();
		log.info("enter modifyPwd with userId={}", user.getId());
		sysUserService.modifyPwd(user.getId(), oldPwd, newPwd);
		return Response.success();
	}
}
