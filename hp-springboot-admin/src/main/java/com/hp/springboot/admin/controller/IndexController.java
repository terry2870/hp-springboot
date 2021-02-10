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
import com.hp.springboot.freemarker.util.FreeMarkerUtil;

/**
 * 描述：首页相关
 * 作者：黄平
 * 时间：2021年1月26日
 */
@RestController
public class IndexController {

	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * @Title: index
	 * @Description: 打开首页
	 * @param session
	 * @return
	 */
	@RequestMapping(AdminConstants.INDEX_URL)
	public ModelAndView index(HttpSession session) {
		log.info("enter index with");
		Map<String, Object> map = new HashMap<>();
		map.put("showPage", "/index/index");
		FreeMarkerUtil.addStaticTemplate(map);
		
		return new ModelAndView("/default", map);
	}
}
