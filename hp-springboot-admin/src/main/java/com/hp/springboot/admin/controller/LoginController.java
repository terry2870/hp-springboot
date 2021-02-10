package com.hp.springboot.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.constant.AdminConstants;

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
		return new ModelAndView("login", map);
	}

	/**
	 * @Title: accessDenied
	 * @Description: 拒绝访问
	 * @param redirectUrl
	 * @return
	 */
	@RequestMapping(AdminConstants.ACCESS_DENIED_URL)
	public ModelAndView accessDenied(String redirectUrl) {
		log.info("enter accessDenied with redirectUrl={}.", redirectUrl);
		Map<String, Object> map = new HashMap<>();
		map.put("redirectUrl", redirectUrl);
		return new ModelAndView("accessDenied", map);
	}
}
