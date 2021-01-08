package com.hp.springboot.database.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：强制使用数据库
 * 作者：黄平
 * 时间：2021-1-7
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface UseDatabase {

	/**
	* @Title: value  
	* @Description: 数据名称
	* @return
	 */
	String value() ;
}
