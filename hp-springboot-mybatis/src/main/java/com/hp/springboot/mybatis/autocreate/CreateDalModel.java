/**
 * 
 */
package com.hp.springboot.mybatis.autocreate;
/**
 * 自动生成model对象
 * @author huangping
 * 2018年5月30日
 */

import java.util.Map;

import com.hp.springboot.freemarker.util.FreeMarkerUtil;
import com.hp.springboot.mybatis.autocreate.helper.TableBean;

public class CreateDalModel {

	/**
	* @Title: create  
	* @Description: 生成model文件
	* @param table
	* @param map
	 */
	public static void create(TableBean table, Map<String, Object> map) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_PACKAGE_NAME + "." + CreateFile.DAL_MODEL_PACKAGE_NAME;
		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.DAL_MAVEN_MODULE + "/" +CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/") + "/"+ table.getModelName() +".java";
		map.put("package", packageUrl);
		FreeMarkerUtil.createFile("autocreate/dalmodel.ftl", fileName, map);
	}
}
