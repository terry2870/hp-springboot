package com.hp.springboot.admin.util;

import java.util.Collection;

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
		if (isAnonymous()) {
			// 匿名用户
			return AdminConstants.ANONYMOUS_USER;
		}
		
		if (isAdmin()) {
			// admin用户
			return AdminConstants.ADMIN_USER;
		}
		
		// 普通用户
		return (SysUserResponseBO) getAuthentication().getPrincipal();
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
}
