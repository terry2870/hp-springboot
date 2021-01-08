package com.hp.springboot.common.exception;

/**
 * 描述：公共的异常类
 * 作者：黄平
 * 时间：2021-1-8
 */
public class CommonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6279256635347472240L;

	private int code;
	private String message;

	public CommonException() {}
	
	public CommonException(int code, String message) {
		super(message);
		this.setCode(code);
		this.setMessage(message);
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
