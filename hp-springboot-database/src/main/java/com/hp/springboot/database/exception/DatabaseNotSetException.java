package com.hp.springboot.database.exception;

/**
 * 描述：没有指定数据库
 * 作者：黄平
 * 时间：2021-1-7
 */
public class DatabaseNotSetException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7653437755158747281L;

	
	public DatabaseNotSetException() {
		super("database is not set.");
	}
}
