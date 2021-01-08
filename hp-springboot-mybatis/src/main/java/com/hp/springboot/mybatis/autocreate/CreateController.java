/**
 * 
 */
package com.hp.springboot.mybatis.autocreate;

import java.util.Map;

import com.hp.springboot.freemarker.util.FreeMarkerUtil;
import com.hp.springboot.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping
 * 2018年6月4日
 */
public class CreateController {

	/**
	* @Title: create  
	* @Description: 生成controller  
	* @param table
	* @param map
	 */
	public static void create(TableBean table, Map<String, Object> map) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.CONTROLLER_PACKAGE_NAME + "." + CreateFile.CONTROLLER_PACKAGE;
		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.CONTROLLER_MAVEN_MODULE + "/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/")
				+ "/" + table.getModelName() + "Controller.java";
		
		map.put("package", packageUrl);
		FreeMarkerUtil.createFile("autocreate/controller.ftl", fileName, map);
	}
}
