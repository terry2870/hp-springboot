package com.hp.springboot.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.admin.util.SecuritySessionUtil;

/**
 * 描述：登录相关接口控制
 * 作者：黄平
 * 时间：2021年1月21日
 */
@RestController
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	/**
	 * @Title: login
	 * @Description: 打开登录页面
	 * @return
	 */
	@RequestMapping(AdminConstants.LOGIN_PAGE_URL)
	public ModelAndView login() {
		log.info("enter login page.");
		Map<String, Object> map = new HashMap<>();
		map.put("loginProcessingUrl", AdminConstants.LOGIN_PROCESSING_URL);
		map.put("indexUrl", AdminConstants.INDEX_URL);
		return new ModelAndView("index/login", map);
	}
	
	/**
	 * @Title: index
	 * @Description: 打开首页
	 * @param session
	 * @return
	 */
	@RequestMapping(AdminConstants.INDEX_URL)
	public ModelAndView index(HttpSession session) {
		log.info("enter index with");
		SysUserResponseBO user = SecuritySessionUtil.getSessionData();
		Map<String, Object> map = new HashMap<>();
		map.put("showPage", "/index/index");
		map.put("user", user);
		return new ModelAndView("/default", map);
	}
}
