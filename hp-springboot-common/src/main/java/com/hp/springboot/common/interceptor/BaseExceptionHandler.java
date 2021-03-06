package com.hp.springboot.common.interceptor;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.springboot.common.bean.Response;
import com.hp.springboot.common.constant.ContentTypeConstant;
import com.hp.springboot.common.exception.CommonException;

/**
 * 描述：全局的异常拦截器
 * 作者：黄平
 * 时间：2021-1-8
 */
@ControllerAdvice
public abstract class BaseExceptionHandler {

	private static Logger log = LoggerFactory.getLogger(BaseExceptionHandler.class);
	
	/**
	 * @Title: handleException
	 * @Description: 单独处理的异常信息
	 * @param request
	 * @param response
	 * @param exception
	 * @return
	 */
	public abstract Response<Object> handleException(HttpServletRequest request, HttpServletResponse response, Exception exception);
	
	/**
	 * @Title: resolveException
	 * @Description: 拦截公共的异常
	 * @param request
	 * @param response
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Response<Object> resolveException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		response.setContentType(ContentTypeConstant.APPLICATION_JSON_UTF8);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		String url = request.getRequestURI();
		if (exception instanceof CommonException) {
			log.warn("enter requestUrl={}. with exception message is: {}", url, exception.getMessage());
			CommonException ex = (CommonException) exception;
			response.setStatus(ex.getCode());
			return new Response<>(ex.getCode(), ex.getMessage());
		}
		
		Response<Object> resp = handleException(request, response, exception);
		if (resp != null) {
			return resp;
		}
		
		log.error("enter requestUrl={}. with exception is:", url, exception);
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return Response.error(exception.getMessage());
	}
}
