package com.hp.springboot.admin.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;

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
		response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
		Response<Object> resp = Response.error(accessDeniedException.getMessage());
		try (PrintWriter out = response.getWriter()) {
			out.write(resp.toString());
			out.flush();
		}
	}

}
