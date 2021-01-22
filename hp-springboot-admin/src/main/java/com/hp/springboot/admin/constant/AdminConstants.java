package com.hp.springboot.admin.constant;

import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.hp.springboot.admin.model.response.SysUserResponseBO;

/**
 * 描述：常量类
 * 作者：黄平
 * 时间：2021年1月12日
 */
public class AdminConstants {

	/**
	 * 用户的角色关系
	 */
	public volatile static MultiValuedMap<Integer, Integer> USER_ROLE_MAP = null;
	
	/**
	 * 菜单对应的角色权限关系
	 */
	public volatile static MultiValuedMap<String, String> MENU_ROLES_MAP = null;
	
	/**
	 * 角色定义的前缀
	 */
	public static final String ROLE_PREFIX = "ROLE_";
	
	/**
	 * admin的角色名称
	 */
	public static final String ADMIN_ROLE_NAME = ROLE_PREFIX + "ADMIN";
	
	/**
	 * 默认的角色名称
	 */
	public static final String DEFAULT_ROLE_NAME = ROLE_PREFIX + "DEFAULT";
	
	/**
	 * 匿名的角色名称
	 */
	public static final String ANONYMOUS_ROLE_NAME = ROLE_PREFIX + "ANONYMOUS";
		
	/**
	 * 登录地址
	 */
	public static final String LOGIN_PAGE_URL = "/login";
	
	/**
	 * 提交登录的页面地址
	 */
	public static final String LOGIN_PROCESSING_URL = "/doLogin";
	
	/**
	 * 注销地址
	 */
	public static final String LOGOUT_URL = "/logout";
	
	/**
	 * index地址
	 */
	public static final String INDEX_URL = "/index";
	
	/**
	 * 匿名用户
	 */
	public static final SysUserResponseBO ANONYMOUS_USER = new SysUserResponseBO("none", "none", "匿名用户", new SimpleGrantedAuthority(ANONYMOUS_ROLE_NAME));
	
	/**
	 * admin用户
	 */
	public static final SysUserResponseBO ADMIN_USER = new SysUserResponseBO("admin", "$2a$10$4rYNtYlYIF5ZQ2oCbc51Q.GlUz.ttD.TiuO7APHi6hdp8D/gpBlNW", "艾德敏", new SimpleGrantedAuthority(ADMIN_ROLE_NAME));
}
