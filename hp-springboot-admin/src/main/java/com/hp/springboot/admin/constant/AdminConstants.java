package com.hp.springboot.admin.constant;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.hp.springboot.admin.model.response.SysUserResponseBO;

/**
 * 描述：常量类
 * 作者：黄平
 * 时间：2021年1月12日
 */
public class AdminConstants {

	/**
	 * 初始密码
	 */
	public static final String NORMAL_USER_DEFAULT_PASSWORD = "$2a$10$4rYNtYlYIF5ZQ2oCbc51Q.GlUz.ttD.TiuO7APHi6hdp8D/gpBlNW";
	
	/**
	 * admin用户初始密码
	 */
	public static final String ADMIN_USER_DEFAULT_PASSWORD = "$2a$10$4rYNtYlYIF5ZQ2oCbc51Q.GlUz.ttD.TiuO7APHi6hdp8D/gpBlNW";
	
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
	 * 出错后跳转地址
	 */
	public static final String ERROR_URL = "/error";
	
	/**
	 * 拒绝访问跳转地址
	 */
	public static final String ACCESS_DENIED_URL = "/accessDenied";
	
	/**
	 * 匿名用户
	 */
	public static final SysUserResponseBO ANONYMOUS_USER = new SysUserResponseBO("none", "none", "匿名用户", new SimpleGrantedAuthority(ANONYMOUS_ROLE_NAME));
	
	/**
	 * admin用户
	 */
	public static final SysUserResponseBO ADMIN_USER = new SysUserResponseBO("admin", ADMIN_USER_DEFAULT_PASSWORD, "艾德敏", new SimpleGrantedAuthority(ADMIN_ROLE_NAME));
	
	/**
	 * 用户菜单色session key（包含按钮）
	 */
	public static final String USER_MENU_INCLUDE_BUTTON = "userMenuIncludeButton";
	
	/**
	 * 用户菜单色session key（不包含按钮）
	 */
	public static final String USER_MENU_EXCLUDE_BUTTON = "userMenuExcludeButton";
	
	/**
	 * 验证码的key
	 */
	public static final String VALIDATE_CODE_KEY = "VALIDATE_CODE";
	
	/**
	 * 用户的session  key
	 */
	public static final String SESSION_USER_KEY = "SESSION_USER";
}
