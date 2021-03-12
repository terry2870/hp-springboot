/**
 * 
 */
package com.hp.springboot.mybatis.autocreate;

import java.util.Map;

import com.hp.springboot.freemarker.util.FreeMarkerUtil;
import com.hp.springboot.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping
 * 2018年6月1日
 */
public class CreateConvert {

	/**
	* @Title: create  
	* @Description: 生成convert
	* @param table
	* @param map
	 */
	public static void create(TableBean table, Map<String, Object> map) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.COMMON_PACKAGE_NAME + "." + CreateFile.CONVERT_PACKAGE_NAME;
		String filePath = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.COMMON_MAVEN_MODULE +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = filePath + "/" + table.getModelName() + "Convert.java";
		map.put("package", packageUrl);
		
		FreeMarkerUtil.createFile("autocreate/convert.ftlh", fileName, map);
	}
}
