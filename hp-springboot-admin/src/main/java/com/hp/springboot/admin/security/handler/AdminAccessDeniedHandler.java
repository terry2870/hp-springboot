package com.hp.springboot.admin.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.util.SecuritySessionUtil;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;
import com.hp.springboot.common.enums.CodeEnum;

/**
 * 描述：登录用户，无权限访问资源异常
 * 作者：黄平
 * 时间：2021年1月19日
 */
public class AdminAccessDeniedHandler implements AccessDeniedHandler {

	private static Logger log = LoggerFactory.getLogger(AdminAccessDeniedHandler.class);
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.warn("AdminAccessDeniedHandler with exception={}", accessDeniedException.getMessage());
		
		response.setStatus(CodeEnum.NO_RIGHT.getCode());
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		if (SecuritySessionUtil.isAjax(request)) {
			// ajax请求
			try (PrintWriter out = response.getWriter()) {
				response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
				Response<Object> resp = Response.error(accessDeniedException.getMessage());
				out.write(resp.toString());
				out.flush();
			}
		} else {
			// 非ajax请求
			response.setContentType(ContentTypeConstant.TEXT_HTML_UTF8);
			response.sendRedirect(request.getContextPath() + AdminConstants.ACCESS_DENIED_URL + "?redirectUrl=" + request.getRequestURI());
		}
	}

}
