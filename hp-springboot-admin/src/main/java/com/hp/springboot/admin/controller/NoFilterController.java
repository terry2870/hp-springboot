/**
 * 
 */
package com.hp.springboot.admin.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.model.response.SysMenuResponseBO;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.bean.EnumTypeRequestBean;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.bean.ValueTextBean;
import com.hp.springboot.common.util.ObjectUtil;
import com.hp.springboot.freemarker.util.FreeMarkerUtil;


/**
 * 描述：只要有session就可以访问的url
 * 作者：黄平
 * 时间：2021年2月1日
 */
@RestController
@RequestMapping("/NoFilterController")
public class NoFilterController {

	private static Logger log = LoggerFactory.getLogger(NoFilterController.class);
	

	/**
	 * 获取枚举的列表
	 * 
	 * @param bo
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=getEnumForSelect")
	public Response<Collection<ValueTextBean>> getEnumForSelect(EnumTypeRequestBean bo) throws Exception {
		log.info("enter getEnumForSelect with EnumTypeRequestBO={}", bo);
		String className = bo.getClassName();
		if (className.indexOf(".") < 0) {
			className = "com.yoho.tools.webjars.enums." + className;
		}
		Collection<ValueTextBean> respList = new ArrayList<>();
		if (bo.getFirstText() != null && bo.getFirstValue() != null) {
			respList.add(new ValueTextBean(bo.getFirstValue(), bo.getFirstText()));
		}
		
		Collection<ValueTextBean> arr = (Collection<ValueTextBean>) ObjectUtil.executeJavaMethod(Class.forName(className), "toJSON", null, null);
		if (CollectionUtils.isNotEmpty(arr)) {
			respList.addAll(arr);
		}
		return Response.success(respList);
	}
	
	/**
	 * @Title: getUserInfo
	 * @Description: 获取登录的用户信息
	 * @return
	 */
	@RequestMapping(params = "method=getUserInfo")
	public Response<SysUserResponseBO> getUserInfo() {
		SysUserResponseBO bo = SecuritySessionUtil.getSessionData();
		return Response.success(bo);
	}
		
	/**
	 * @Title: queryUserMenuFromSession
	 * @Description: 查询用户的权限菜单（从session中获取）
	 * @param session
	 * @return
	 */
	@RequestMapping(params = "method=queryUserMenuFromSession")
	public Response<Object> queryUserMenuFromSession(HttpSession session) {
		Object menuList = session.getAttribute(AdminConstants.USER_MENU_EXCLUDE_BUTTON);
		return Response.success(menuList);
	}
	
	/**
	 * @Title: querySessionButtonByMenuId
	 * @Description: 根据菜单id，查询该菜单下的按钮
	 * @param session
	 * @param menuId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "method=querySessionButtonByMenuId")
	public Response<List<SysMenuResponseBO>> querySessionButtonByMenuId(HttpSession session, Integer menuId) {
		List<SysMenuResponseBO> list = (List<SysMenuResponseBO>) session.getAttribute(AdminConstants.USER_MENU_INCLUDE_BUTTON);
		List<SysMenuResponseBO> respList = new ArrayList<>();
		for (SysMenuResponseBO bo : list) {
			if (bo.getParentMenuId().intValue() == menuId.intValue()) {
				respList.add(bo);
			}
		}
		return Response.success(respList);
	}

	/**
	 * @Title: forward
	 * @Description: 跳转到地址
	 * @param request
	 * @param redirectUrl
	 * @return
	 */
	@RequestMapping(params = "method=forward", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView forward(HttpServletRequest request, String redirectUrl) {
		Map<String, String[]> parameters = request.getParameterMap();
		Map<String, Object> map = new HashMap<>();
		if (MapUtils.isNotEmpty(parameters)) {
			for (Entry<String, String[]> entry : parameters.entrySet()) {
				if (entry.getValue() != null) {
					map.put(entry.getKey(), entry.getValue()[0]);
				}
			}
		}
		FreeMarkerUtil.addStaticTemplate(map);
		return new ModelAndView(redirectUrl, map);
	}
}
