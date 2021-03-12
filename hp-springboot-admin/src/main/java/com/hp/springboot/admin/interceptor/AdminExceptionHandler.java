package com.hp.springboot.admin.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.springboot.admin.constant.AdminConstants;
import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;
import com.hp.springboot.common.interceptor.BaseExceptionHandler;
import com.hp.springboot.common.util.ServletUtil;

/**
 * 描述：
 * 作者：黄平
 * 时间：2021年1月11日
 */
public class AdminExceptionHandler extends BaseExceptionHandler {

	@Override
	public Response<Object> handleException(HttpServletRequest request, HttpServletResponse response,
			Exception exception) {
		return null;
	}
	
	/**
	 * @Title: resolveException
	 * @Description: 拦截公共的异常
	 * @param request
	 * @param response
	 * @param exception
	 * @return
	 */
	@Override
	public Response<Object> resolveException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		if (!ServletUtil.isAjax(request)) {
			// 非ajax请求
			response.setContentType(ContentTypeConstant.TEXT_HTML_UTF8);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			try {
				response.sendRedirect(request.getContextPath() + AdminConstants.ACCESS_DENIED_URL + "?redirectUrl=" + request.getRequestURI());
			} catch (IOException e) {
			}
			return null;
		} else {
			return super.resolveException(request, response, exception);
		}
	}

}
