/**
 * 
 */
package com.hp.springboot.mybatis.autocreate;

import java.util.Map;

import com.hp.springboot.freemarker.util.FreeMarkerUtil;
import com.hp.springboot.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping
 * 2018年5月31日
 */
public class CreateDAO {

	/**
	* @Title: create  
	* @Description: 生成model文件
	* @param table
	 */
	public static void create(TableBean table, Map<String, Object> map) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAO_PACKAGE_NAME;
		String fileName = CreateFile.MAIN_PATH_DIR + "/"+ CreateFile.DAO_MAVEN_MODULE +"/" +CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/") + "/I"+ table.getModelName() +"DAO.java";
		map.put("package", packageUrl);
		FreeMarkerUtil.createFile("autocreate/dao.ftl", fileName, map);
	}
}
