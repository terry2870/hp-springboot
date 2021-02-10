package com.hp.springboot.admin.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import com.google.common.collect.Lists;
import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.model.response.SysMenuResponseBO;
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.constant.GoogleContant;

/**
 * 描述：
 * 作者：黄平
 * 时间：2021年1月29日
 */
public class UrlAuthenticationInterceptor {
		
	//默认的第二免过滤
	private static final List<String> defaultSecondNoFilterList = Lists.newArrayList(AdminConstants.INDEX_URL, "/NoFilterController");
	
	// 第二级免过滤列表（只要有session就都可以访问）
	@Value("#{'${hp.springboot.admin.second-no-filter-urls:}'.split(',')}")
	private List<String> secondNoFilterList;
		
	/**
	 * @Title: hasPermission
	 * @Description: 检查是否有权限
	 * @param request
	 * @param authentication
	 * @return
	 */
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		String url = request.getRequestURI();
		
		//去掉contextPath
		url = StringUtils.substringAfter(url, request.getContextPath());
		
		if (SecuritySessionUtil.isAnonymous()) {
			// 匿名用户直接返回
			throw new InsufficientAuthenticationException("");
		}
		
		if (SecuritySessionUtil.isAdmin()) {
			// admin 用户，直接通过
			return true;
		}
		
		if (CollectionUtils.containsAny(defaultSecondNoFilterList, url)) {
			// 默认的二级免过滤列表
			return true;
		}
		
		if (CollectionUtils.isNotEmpty(secondNoFilterList) && !(secondNoFilterList.size() == 1 && StringUtils.isEmpty(secondNoFilterList.get(0)))
				&& CollectionUtils.containsAny(secondNoFilterList, url)) {
			// 配置文件的二级免过滤列表
			return true;
		}
		
		// 按照权限进行过滤
		@SuppressWarnings("unchecked")
		List<SysMenuResponseBO> userMenu = (List<SysMenuResponseBO>) request.getSession().getAttribute(AdminConstants.USER_MENU_INCLUDE_BUTTON);
		for (SysMenuResponseBO bo : userMenu) {
			if (StringUtils.isNotEmpty(bo.getMenuUrl()) && StringUtils.equals(bo.getMenuUrl(), url)) {
				// 匹配菜单url
				return true;
			}
			
			if (StringUtils.isEmpty(bo.getExtraUrl())) {
				continue;
			}
			
			// 查找额外url
			List<String> extraUrlList = GoogleContant.COMMA_SPLITTER.splitToList(bo.getExtraUrl());
			for (String s : extraUrlList) {
				if (StringUtils.equals(s, url)) {
					return true;
				}
			}
		}
		
		// 走到这，说明没有匹配上，没有权限访问
		throw new AccessDeniedException("您没有权限访问url【"+ url +"】!");
	}

}
