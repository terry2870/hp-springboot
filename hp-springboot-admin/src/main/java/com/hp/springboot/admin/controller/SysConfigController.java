package com.hp.springboot.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.model.request.SysConfigRequestBO;
import com.hp.springboot.admin.model.response.SysConfigResponseBO;
import com.hp.springboot.admin.service.ISysConfigService;
import com.hp.springboot.common.bean.PageResponse;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.enums.StatusEnum;
import com.hp.springboot.database.bean.PageRequest;
import com.hp.springboot.freemarker.util.FreeMarkerUtil;

/**
 * 描述：系统配置控制器
 * 作者：黄平
 * 时间：2021年2月25日
 */
@RestController
@RequestMapping("/SysConfigController")
public class SysConfigController {

	private static Logger log = LoggerFactory.getLogger(SysConfigController.class);
	
	@Autowired
	private ISysConfigService sysConfigService;
	
	/**
	 * @Title: sysConfigList
	 * @Description: 打开用户列表页面
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/sysConfigList")
	public ModelAndView sysConfigList(String menuId) {
		Map<String, Object> map = new HashMap<>();
		FreeMarkerUtil.addStaticTemplate(map);
		map.put("showPage", "sysManage/sysConfig/sysConfigList");
		return new ModelAndView("/default", map);
	}
	
	/**
	 * @Title: querySysConfigPageList
	 * @Description: 查询配置列表
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/querySysConfigPageList")
	public Response<PageResponse<SysConfigResponseBO>> querySysConfigPageList(SysConfigRequestBO request, PageRequest pageRequest) {
		log.info("enter querySysConfigPageList with request={}, pageRequest={}.", request, pageRequest);
		PageResponse<SysConfigResponseBO> list = sysConfigService.querySysConfigPageList(request, pageRequest);
		return Response.success(list);
	}
	
	/**
	 * @Title: saveSysConfig
	 * @Description: 保存系统配置
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSysConfig")
	public Response<Object> saveSysConfig(SysConfigRequestBO request) {
		log.info("enter saveSysConfig with request={}.", request);
		sysConfigService.saveSysConfig(request);
		return Response.success();
	}
	
	/**
	 * @Title: querySysConfigById
	 * @Description: 根据id，查询系统配置详情
	 * @param id
	 * @return
	 */
	@RequestMapping("/querySysConfigById")
	public Response<SysConfigResponseBO> querySysConfigById(Integer id) {
		log.info("enter querySysConfigById with id={}", id);
		SysConfigResponseBO bo = sysConfigService.querySysConfigById(id);
		return Response.success(bo);
	}
	
	/**
	 * @Title: deleteSysConfig
	 * @Description: 删除系统配置
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSysConfig")
	public Response<Object> deleteSysConfig(Integer id) {
		log.info("enter deleteSysConfig with id={}", id);
		sysConfigService.deleteSysConfig(id);
		return Response.success();
	}
	
	/**
	 * @Title: changeSysConfigStatus
	 * @Description: 修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/changeSysConfigStatus")
	public Response<Object> changeSysConfigStatus(Integer id, Integer status) {
		log.info("enter changeSysConfigStatus with id={}", id);
		sysConfigService.changeSysConfigStatus(id, StatusEnum.getEnumByValue(status));
		return Response.success();
	}
}
