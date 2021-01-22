package com.hp.springboot.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.interceptor.BaseExceptionHandler;

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

}
