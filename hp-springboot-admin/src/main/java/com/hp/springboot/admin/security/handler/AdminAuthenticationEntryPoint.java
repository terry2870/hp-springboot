package com.hp.springboot.admin.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;
import com.hp.springboot.common.enums.CodeEnum;

/**
 * 描述：匿名用户无权限访问异常
 * 作者：黄平
 * 时间：2021年1月21日
 */
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static Logger log = LoggerFactory.getLogger(AdminAuthenticationEntryPoint.class);
	
	/**
	 * 匿名用户访问无权限资源，直接跳到登录页面
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.warn("AdminAuthenticationEntryPoint with exception={}", authException.getMessage());
		
		response.setStatus(CodeEnum.NO_RIGHT.getCode());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		try (PrintWriter out = response.getWriter()) {
			if (SecuritySessionUtil.isAjax(request)) {
				// ajax请求
				response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
				Response<Object> resp = Response.error(CodeEnum.SESSION_TIME_OUT.getCode(), "");
				out.write(resp.toString());
				out.flush();
			} else {
				// 非ajax请求
				response.setContentType(ContentTypeConstant.TEXT_HTML_UTF8);
				out.write("<script>alert('您没有权限访问该资源，请您登录后访问！');location.href='"+ request.getContextPath() + AdminConstants.LOGIN_PAGE_URL +"'</script>");
				out.flush();
			}
		}
	}

}
