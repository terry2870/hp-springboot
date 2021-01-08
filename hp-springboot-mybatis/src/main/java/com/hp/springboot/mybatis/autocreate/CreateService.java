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
public class CreateService {

	/**
	* @Title: createInterface  
	* @Description: 生成接口文件
	* @param table
	* @param map
	 */
	public static void createInterface(TableBean table, Map<String, Object> map) {
		String packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_PACKAGE_NAME + "." + CreateFile.SERVICE_PACKAGE;
		String filePath = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.SERVICE_MAVEN_MODULE +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = "I" + table.getModelName() + "Service.java";
		map.put("package", packageUrl);
		FreeMarkerUtil.createFile("autocreate/service.ftl", filePath + "/" + fileName, map);
	}
	
	/**
	* @Title: createInterfaceImpl  
	* @Description: 生成接口实现类
	* @param table
	* @param map
	 */
	public static void createInterfaceImpl(TableBean table, Map<String, Object> map) {
		String servicePackage = CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_PACKAGE_NAME + "." + CreateFile.SERVICE_PACKAGE;
		String packageUrl = servicePackage + "." + CreateFile.IMPL_PACKAGE_NAME;
		String filePath = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.SERVICE_MAVEN_MODULE +"/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		String fileName = table.getModelName() + "ServiceImpl.java";
		map.put("package", packageUrl);
		FreeMarkerUtil.createFile("autocreate/serviceimpl.ftl", filePath + "/" + fileName, map);
	}
}
