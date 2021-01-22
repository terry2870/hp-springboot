package com.hp.springboot.admin.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hp.springboot.admin.constant.AdminConstants;

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
		if (!StringUtils.equalsIgnoreCase(url, request.getContextPath() + AdminConstants.LOGIN_PROCESSING_URL)) {
			// 不是登录接口，不要校验
			filterChain.doFilter(request, response);
			return;
		}
		
		// 需要校验验证码
		System.out.println("url= " + url);
		System.out.println("ValidateCodeFilter= " + this);
		if (true) {
			//throw new AuthenticationServiceException("asdasd");
		}
		filterChain.doFilter(request, response);
	}

}
