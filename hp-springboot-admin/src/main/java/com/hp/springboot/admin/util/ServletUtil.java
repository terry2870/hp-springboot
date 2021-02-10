package com.hp.springboot.admin.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 描述：通用的servlet处理类
 * 作者：黄平
 * 时间：2021年1月29日
 */
public class ServletUtil {

	/**
	 * @Title: getRequest
	 * @Description: 获取request
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}
	
	/**
	 * @Title: getResponse
	 * @Description: 获取response
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}
	
	/**
	 * @Title: getSession
	 * @Description: 获取session
	 * @return
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	private static ServletRequestAttributes getRequestAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}
}
