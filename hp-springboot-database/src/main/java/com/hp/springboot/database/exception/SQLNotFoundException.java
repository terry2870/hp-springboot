package com.hp.springboot.database.exception;

/**
 * 描述：找不到sql异常
 * 作者：黄平
 * 时间：2020-12-28
 */
public class SQLNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4797995234469423068L;

	public SQLNotFoundException() {
		super("sql is not find");
	}
}
