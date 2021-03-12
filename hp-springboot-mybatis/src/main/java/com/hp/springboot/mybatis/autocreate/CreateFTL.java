/**
 * 
 */
package com.hp.springboot.mybatis.autocreate;

import java.util.Map;

import com.hp.springboot.freemarker.util.FreeMarkerUtil;
import com.hp.springboot.mybatis.autocreate.helper.TableBean;

/**
 * @author huangping 2018年7月11日
 */
public class CreateFTL {

	/**
	 * 生成jsp列表
	 * @param table
	 * @param map
	 */
	public static void createFTLList(TableBean table, Map<String, Object> map) {
		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_MAVEN_MODULE_NAME + "/" + CreateFile.FTL_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "List.ftlh";
		FreeMarkerUtil.createFile("autocreate/pageList.ftlh", fileName, map);
	}

	/**
	 * 生成编辑页面
	 * @param table
	 * @param map
	 */
	public static void createFTLEdit(TableBean table, Map<String, Object> map) {

		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_MAVEN_MODULE_NAME + "/" + CreateFile.FTL_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "Edit.ftlh";
		FreeMarkerUtil.createFile("autocreate/pageEdit.ftlh", fileName, map);
	}

	/**
	 * 生成查询页面
	 * @param table
	 * @param map
	 */
	public static void createFTLSearch(TableBean table, Map<String, Object> map) {

		String fileName = CreateFile.MAIN_PATH_DIR + "/" + CreateFile.WEB_MAVEN_MODULE_NAME + "/" + CreateFile.FTL_FILE_PATH
				+ "/" + table.getModelNameFirstLow() + "Search.ftlh";
		FreeMarkerUtil.createFile("autocreate/pageSearch.ftlh", fileName, map);
	}
}
