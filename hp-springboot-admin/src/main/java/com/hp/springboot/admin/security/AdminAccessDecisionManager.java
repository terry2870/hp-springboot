package com.hp.springboot.admin.security;

import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import com.hp.springboot.admin.util.SecuritySessionUtil;

/**
 * 描述：自定义权限决策管理器
 * decide方法，先查询此用户当前拥有的权限，然后与上面过滤器核查出来的权限列表作对比
 * 以此判断此用户是否具有这个访问权限，决定去留！所以顾名思义为权限决策器
 * 作者：黄平
 * 时间：2021年1月12日
 */
public class AdminAccessDecisionManager implements AccessDecisionManager {
	
	/**
	 * 权限决策器
	 * authentication：当前用户的权限
	 * object：对象信息，获取request，response
	 * configAttributes：访问该菜单需要的权限
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		// 本次请求的url
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		
		if (authentication == null) {
			throw new InsufficientAuthenticationException("匿名用户，不允许访问该资源。");
		}
		
		if (SecuritySessionUtil.isAnonymous()) {
			// 匿名用户
			throw new InsufficientAuthenticationException("匿名用户，不允许访问该资源。");
		}
		
		// 当前用户所拥有的权限
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if (CollectionUtils.isEmpty(authorities)) {
			throw new AccessDeniedException("您没有权限访问：【"+ requestUrl +"】");
		}
		
		// 菜单的权限转为数组
		String[] menuArr = new String[configAttributes.size()];
		int i = 0;
		for (ConfigAttribute ca : configAttributes) {
			menuArr[i] = ca.getAttribute();
			i++;
		}
		
		// 遍历当前用户的权限，只要有一个在这个菜单的权限中，就可以访问
		for (GrantedAuthority userAuthority : authorities) {
			if (StringUtils.equalsAny(userAuthority.getAuthority(), menuArr)) {
				// 只要匹配一个，就可以访问
				return;
			}
		}
		
		// 这个时候，用户已经登录，但是没有权限访问这个菜单
		// 走到这里，说明，权限没有匹配上
		throw new AccessDeniedException("您没有权限访问：【"+ requestUrl +"】");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
