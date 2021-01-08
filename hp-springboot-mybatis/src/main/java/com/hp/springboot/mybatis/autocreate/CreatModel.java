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
public class CreatModel {

	/**
	* @Title: creat  
	* @Description: 生成request的model
	* @param table
	* @param type
	* @param map
	 */
	public static void creat(TableBean table, String type, Map<String, Object> map) {
		String packageUrl = "", fileName = "", filePath = "", baseExtendBeanName = "", importPackage = "";
		if ("1".equals(type)) {
			//request
			packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_PACKAGE_NAME + "." + CreateFile.REQUEST_PACKAGE_NAME;
			fileName = table.getModelName() + "RequestBO";
			baseExtendBeanName = "BaseRequestBO";
			importPackage = CreateFile.BASE_REQUEST_BO_PACKAGE;
		} else if ("2".equals(type)) {
			//response
			packageUrl = CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_PACKAGE_NAME + "." + CreateFile.RESPONSE_PACKAGE_NAME;
			fileName = table.getModelName() + "ResponseBO";
			baseExtendBeanName = "BaseResponseBO";
			importPackage = CreateFile.BASE_RESPONSE_BO_PACKAGE;
		}
		filePath = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.MODEL_MAVEN_MODULE + "/" + CreateFile.JAVA_DIR + "/" + packageUrl.replace(".", "/");
		map.put("package", packageUrl);
		map.put("className", fileName);
		map.put("importPackage", importPackage);
		map.put("baseExtendBeanName", baseExtendBeanName);
		
		FreeMarkerUtil.createFile("autocreate/model.ftl", filePath + "/" + fileName + ".java", map);
	}
}
