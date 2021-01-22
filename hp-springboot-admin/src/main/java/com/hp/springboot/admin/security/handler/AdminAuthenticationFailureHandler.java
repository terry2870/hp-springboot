package com.hp.springboot.admin.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.hp.springboot.admin.exception.ValidateCodeException;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;

/**
 * 描述：登录失败处理 作者：黄平 时间：2021年1月15日
 */
public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static Logger log = LoggerFactory.getLogger(AdminAuthenticationFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.warn("login error with exception is {}", exception.getMessage());
		response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
		String message = "";
		if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
			message = "账户名或者密码输入错误!";
		} else if (exception instanceof LockedException) {
			message = "账户被锁定，请联系管理员!";
		} else if (exception instanceof CredentialsExpiredException) {
			message = "密码过期，请联系管理员!";
		} else if (exception instanceof AccountExpiredException) {
			message = "账户过期，请联系管理员!";
		} else if (exception instanceof DisabledException) {
			message = "账户被禁用，请联系管理员!";
		} else if (exception instanceof ValidateCodeException) {
			message = "验证码输入错误，请重新输入！";
		} else if (exception instanceof InsufficientAuthenticationException) {
			message = exception.getMessage();
		} else {
			message = "登录失败!";
		}
		
		Response<Object> resp = Response.error(message);
		try (PrintWriter out = response.getWriter()) {
			out.write(resp.toString());
			out.flush();
		}
	}

}
