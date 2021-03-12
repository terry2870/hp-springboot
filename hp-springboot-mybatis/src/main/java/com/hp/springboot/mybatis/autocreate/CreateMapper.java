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
public class CreateMapper {

	/**
	* @Title: create  
	* @Description: 生成mapper文件
	* @param table
	* @param map
	 */
	public static void create(TableBean table, Map<String, Object> map) {
		String fileName = CreateFile.MAIN_PATH_DIR + "/"+ CreateFile.DAO_MAVEN_MODULE +"/" + CreateFile.MAPPING_DIR + "/"+ table.getModelName() +"Mapper.xml";
		FreeMarkerUtil.createFile("autocreate/mapper.ftlh", fileName, map);
	}
}
