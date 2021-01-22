package com.hp.springboot.admin.security;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.google.common.collect.Lists;
import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.service.ISysMenuService;
import com.hp.springboot.admin.util.SecuritySessionUtil;

/**
 * 描述：自定义权限过滤器
 * Spring Security是通过SecurityMetadataSource来加载访问时所需要的具体权限
 * 自定义权限资源过滤器，实现动态的权限验证
 * 它的主要责任就是当访问一个url时，返回这个url所需要的访问权限
 * 作者：黄平
 * 时间：2021年1月12日
 */
public class AdminFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private ISysMenuService sysMenuService;
	
	// 第二级免过滤列表（只要有session就都可以访问）
	@Value("#{'${hp.springboot.admin.second-no-filter-urls:}'.split(',')}")
	private List<String> secondNoFilterList;
	
	// 默认的第二免过滤
	private static final List<String> defaultSecondNoFilterList = Lists.newArrayList(AdminConstants.INDEX_URL, "/NoFilterController", "/RedirectController");

	/**
	 * 获取默认的角色
	 */
	private static Collection<ConfigAttribute> DEFAULT_ROLE = SecurityConfig.createList(AdminConstants.DEFAULT_ROLE_NAME);
	
	/**
	 * 匿名角色
	 */
	private static Collection<ConfigAttribute> ANONYMOUS_ROLE = SecurityConfig.createList(AdminConstants.ANONYMOUS_ROLE_NAME);
	
	/**
	 * 返回本次访问需要的权限，可以有多个权限
	 * 进入这个方法的菜单，所有都需要登录session，如果没有session，就直接拒绝
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		// 本次请求的url
		FilterInvocation filterInvocation = (FilterInvocation) object;
		String requestUrl = filterInvocation.getRequestUrl();
		
		if (SecuritySessionUtil.isAnonymous()) {
			//匿名用户
			return ANONYMOUS_ROLE;
		}
		
		//检查二级免过滤列表
		if (checkSecondNoFilter(requestUrl)) {
			// 属于二级免过滤url，则直接放行
			return null;
		}
		
		// 检查是否是admin角色
		if (SecuritySessionUtil.isAdmin()) {
			// admin角色 直接放行
			return null;
		}
		
		// 查询所有菜单需要的权限
		MultiValuedMap<String, String> menuRoleMap = sysMenuService.queryAllMenuRoles();
		if (menuRoleMap == null) {
			// 返回默认权限
			return DEFAULT_ROLE;
		}
		
		// 获取当前菜单需要的权限
		Collection<String> roleIdList = menuRoleMap.get(requestUrl);
		if (CollectionUtils.isEmpty(roleIdList)) {
			// 返回默认权限
			return DEFAULT_ROLE;
		}
		
		// 返回权限
		return SecurityConfig.createList(roleIdList.toArray(new String[] {}));
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 方法返回类对象是否支持校验
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	/**
	 * @Title: checkSecondNoFilter
	 * @Description: 二级免过滤列表检查
	 * @param requestUrl
	 * @return
	 */
	private boolean checkSecondNoFilter(String requestUrl) {
		return secondNoFilterList.contains(requestUrl) || defaultSecondNoFilterList.contains(requestUrl);
	}
}
