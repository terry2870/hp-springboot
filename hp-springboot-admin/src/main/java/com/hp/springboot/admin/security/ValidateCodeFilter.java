package com.hp.springboot.admin.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.admin.exception.ValidateCodeException;
import com.hp.springboot.admin.security.handler.AdminAuthenticationFailureHandler;
import com.hp.springboot.common.util.SpringContextUtil;

/**
 * 描述：检查验证码的过滤器
 * 作者：黄平
 * 时间：2021年1月13日
 */
public class ValidateCodeFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		System.out.println("ValidateCodeFilter with url= " + url);
		if (!StringUtils.equalsIgnoreCase(url, request.getContextPath() + AdminConstants.LOGIN_PROCESSING_URL)) {
			// 不是登录接口，不要校验
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			//validate(request);
		} catch (AuthenticationException e) {
			AdminAuthenticationFailureHandler adminAuthenticationFailureHandler = SpringContextUtil.getBean(AdminAuthenticationFailureHandler.class);
			adminAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
			return;
		}
		
		filterChain.doFilter(request, response);
	}

	/**
	 * @Title: validate
	 * @Description: 验证验证码是否正确
	 * @param request
	 */
	private void validate(HttpServletRequest request) {
		// 页面输入的验证码
		String inputValidateCode = request.getParameter(AdminConstants.VALIDATE_CODE_KEY);
		
		if (StringUtils.isEmpty(inputValidateCode)) {
			throw new ValidateCodeException("请输入验证码！");
		}
		
		// 保存在session中的验证码
		String sessionValidateCode = (String) request.getSession().getAttribute(AdminConstants.VALIDATE_CODE_KEY);
		
		if (!StringUtils.equalsIgnoreCase(inputValidateCode, sessionValidateCode)) {
			throw new ValidateCodeException();
		}
		
		// 验证码正确
	}
}
