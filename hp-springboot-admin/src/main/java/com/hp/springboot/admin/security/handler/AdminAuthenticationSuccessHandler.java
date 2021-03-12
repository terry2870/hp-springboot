package com.hp.springboot.admin.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.enums.MenuTypeEnum;
import com.hp.springboot.admin.model.response.SysMenuResponseBO;
import com.hp.springboot.admin.model.response.SysUserResponseBO;
import com.hp.springboot.admin.service.ISysMenuService;
import com.hp.springboot.admin.service.ISysUserService;
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;
import com.hp.springboot.common.enums.StatusEnum;

/**
 * 描述：登录成功处理类
 * 作者：黄平
 * 时间：2021年1月22日
 */
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private ISysMenuService sysMenuService;
	@Value("${project.name.cn}")
	private String projectName;
	@Autowired
	private ISysUserService sysUserService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
		
		HttpSession session = request.getSession();
		
		//设置登录用户session
		setUserSession(session);
		
		//查询用户的菜单和按钮
		setUserMenu(session);
		
		SysUserResponseBO user = SecuritySessionUtil.getSessionData();
		
		// 更新最近登录时间
		sysUserService.updateLastLoginTime(user.getId());
		
		//项目名称
		session.setAttribute("projectName", projectName);
		
		// 注销地址
		session.setAttribute("logoutUrl", AdminConstants.LOGOUT_URL);
		
		Response<Object> resp = Response.success();
		try (PrintWriter out = response.getWriter()) {
			out.write(resp.toString());
			out.flush();
		}
	}
	
	/**
	 * @Title: setUserSession
	 * @Description: 设置登录用户session
	 * @param request
	 */
	private void setUserSession(HttpSession session) {
		SysUserResponseBO user = null;
		if (SecuritySessionUtil.isAdmin()) {
			// admin用户
			user = AdminConstants.ADMIN_USER;
		} else {
			// 普通用户
			user = (SysUserResponseBO) SecuritySessionUtil.getAuthentication().getPrincipal();
		}
		// 设置session
		session.setAttribute(AdminConstants.SESSION_USER_KEY, user);
	}

	/**
	 * @Title: getUserMenu
	 * @Description: 把用户菜单放入session
	 * @param session
	 */
	private void setUserMenu(HttpSession session) {		
		// 查询数据库
		List<SysMenuResponseBO> menuIncludeButtonList = sysMenuService.queryUserSysMenu(StatusEnum.VALID);
		if (menuIncludeButtonList == null) {
			menuIncludeButtonList = new ArrayList<>();
		}
		
		//放入session
		session.setAttribute(AdminConstants.USER_MENU_INCLUDE_BUTTON, menuIncludeButtonList);
		
		// 按钮
		List<String> buttonIdList = new ArrayList<>();
		
		// 排除按钮
		List<SysMenuResponseBO> menuExcludeButtonList = new ArrayList<>();
		for (SysMenuResponseBO menu : menuIncludeButtonList) {
			if (MenuTypeEnum.BUTTON.getIntegerValue().equals(menu.getMenuType())) {
				buttonIdList.add(menu.getButtonId());
				continue;
			}
			menuExcludeButtonList.add(menu);
		}
		
		//放入session
		session.setAttribute(AdminConstants.USER_MENU_EXCLUDE_BUTTON, menuExcludeButtonList);
		session.setAttribute(AdminConstants.USER_MENU_ONLY_BUTTON, buttonIdList);
	}
}
