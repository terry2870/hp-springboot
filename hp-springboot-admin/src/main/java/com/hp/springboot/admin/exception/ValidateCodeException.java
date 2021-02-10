package com.hp.springboot.admin.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 描述：验证码验证失败异常
 * 作者：黄平
 * 时间：2021年1月18日
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5628791216406527401L;

	
	public ValidateCodeException() {
		super("验证码输入不正确！");
	}
	
	public ValidateCodeException(String message) {
		super(message);
	}
}
