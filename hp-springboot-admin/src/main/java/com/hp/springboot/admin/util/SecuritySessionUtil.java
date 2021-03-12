package com.hp.springboot.admin.util;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.model.response.SysUserResponseBO;

/**
 * 描述：session的操作类
 * 作者：黄平
 * 时间：2021年1月19日
 */
public class SecuritySessionUtil {

	/**
	 * @Title: getAuthentication
	 * @Description: 获取当前用户信息
	 * @return
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	/**
	 * @Title: getSessionData
	 * @Description: 获取session
	 * @return
	 */
	public static SysUserResponseBO getSessionData() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		if (isAnonymous()) {
			// 匿名用户
			return AdminConstants.ANONYMOUS_USER;
		}
		
		// 登录用户
		return (SysUserResponseBO) ServletUtil.getSession().getAttribute(AdminConstants.SESSION_USER_KEY);
	}
	
	/**
	 * @Title: isAnonymous
	 * @Description: 是否是匿名用户
	 * @return
	 */
	public static boolean isAnonymous() {
		return getAuthentication() instanceof AnonymousAuthenticationToken;
	}
	
	/**
	 * @Title: checkAdmin
	 * @Description: 检查是否是admin角色用户
	 * admin用户只有一个角色
	 * @param authentication
	 * @return
	 */
	public static boolean isAdmin() {
		if (isAnonymous()) {
			// 匿名用户
			return false;
		}
		
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return false;
		}
		Collection<? extends GrantedAuthority>  authoritiesList = authentication.getAuthorities();
		if (CollectionUtils.isEmpty(authoritiesList) || authoritiesList.size() != 1) {
			// admin用户只有一个admin的角色
			return false;
		}
		
		GrantedAuthority grantedAuthority = authoritiesList.iterator().next();
		if (StringUtils.equals(grantedAuthority.getAuthority(), AdminConstants.ADMIN_ROLE_NAME)) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Title: isAjax
	 * @Description: 判断是否为ajax
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return StringUtils.equalsIgnoreCase(request.getHeader("X-Requested-With"), "XMLHttpRequest");
	}
	
	/**
	 * @Title: checkButtonEnabled
	 * @Description: 检查是否有按钮权限
	 * @param buttonId
	 * @return
	 */
	public static boolean checkButtonEnabled(String buttonId) {
		if (StringUtils.isEmpty(buttonId)) {
			return false;
		}
		
		//获取所有button权限
		@SuppressWarnings("unchecked")
		List<String> buttonIdList = (List<String>) ServletUtil.getSession().getAttribute(AdminConstants.USER_MENU_ONLY_BUTTON);
		if (CollectionUtils.isEmpty(buttonIdList)) {
			return false;
		}
		
		return CollectionUtils.containsAny(buttonIdList, buttonId);
	}
}
