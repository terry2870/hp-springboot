package com.hp.springboot.admin.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;

/**
 * 描述：登录成功处理类
 * 作者：黄平
 * 时间：2021年1月22日
 */
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
		Response<Object> resp = Response.success();
		try (PrintWriter out = response.getWriter()) {
			out.write(resp.toString());
			out.flush();
		}
	}

}
