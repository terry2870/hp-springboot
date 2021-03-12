package com.hp.springboot.admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.util.VerifyCodeUtils;
import com.hp.springboot.freemarker.util.FreeMarkerUtil;

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
		FreeMarkerUtil.addStaticTemplate(map);
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
	
	/**
	 * @Title: verifyCode
	 * @Description: 生成图形验证码
	 * @param request
	 * @param response
	 */
	@RequestMapping(AdminConstants.VERIFY_CODE_URL)
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = VerifyCodeUtils.createImage(response.getOutputStream(), 4);
			//保存到session中
			request.getSession().setAttribute(AdminConstants.VALIDATE_CODE_KEY, code);
		} catch (IOException e) {
		}
	}
}
