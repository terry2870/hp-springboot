package com.hp.springboot.admin.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;

/**
 * 描述：匿名用户无权限访问异常
 * 作者：黄平
 * 时间：2021年1月21日
 */
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static Logger log = LoggerFactory.getLogger(AdminAuthenticationEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.warn("AdminAuthenticationEntryPoint with exception={}", authException.getMessage());
		response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
		Response<Object> resp = Response.error(authException.getMessage());
		try (PrintWriter out = response.getWriter()) {
			out.write(resp.toString());
			out.flush();
		}
	}

}
