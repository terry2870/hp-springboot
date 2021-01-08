/**
 * 
 */
package com.hp.springboot.mybatis.autocreate;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.springboot.common.util.SpringContextUtil;
import com.hp.springboot.database.datasource.DynamicDatasource;
import com.hp.springboot.mybatis.autocreate.helper.AutoCreateBean;
import com.hp.springboot.mybatis.autocreate.helper.TableBean;
import com.hp.springboot.mybatis.autocreate.helper.TableBeanHelper;

/**
 * @author huangping
 * 2018年5月30日
 */
public class CreateFile {
	
	private static Logger log = LoggerFactory.getLogger(CreateFile.class);

	//文件生成的主路径
	public static String MAIN_PATH_DIR;
	
	//java文件存放主目录
	public static String JAVA_DIR;
	
	public static final String FTL_FILE_PATH = "src/main/resources/templates/other";

	public static String PROJECT_PACKAGE;

	public static String MAPPING_DIR;
	
	// maven module 的命名
	public static String WEB_MAVEN_MODULE_NAME;
	public static String SERVICE_MAVEN_MODULE;
	public static String CONTROLLER_MAVEN_MODULE;
	public static String DAL_MAVEN_MODULE;
	public static String COMMON_MAVEN_MODULE;
	public static String MODEL_MAVEN_MODULE;
	
	// 项目中，各个模块对应的包名称
	public static String DAL_PACKAGE_NAME;
	public static String COMMON_PACKAGE_NAME;
	public static String MODEL_PACKAGE_NAME;
	public static String SERVICE_PACKAGE_NAME;
	public static String CONTROLLER_PACKAGE_NAME;
	
	
	private static final String BASE_PACKAGE_PARENT = "com.hp.springboot.";
	
	public static final String BASE_BEAN_PACKAGE = BASE_PACKAGE_PARENT + "common.bean.AbstractBean";
	public static final String BASE_REQUEST_BO_PACKAGE = BASE_PACKAGE_PARENT + "common.bean.BaseRequestBO";
	public static final String BASE_RESPONSE_BO_PACKAGE = BASE_PACKAGE_PARENT + "common.bean.BaseResponseBO";
	public static final String BASE_MAPPER_PACKAGE = BASE_PACKAGE_PARENT + "mybatis.mapper.BaseMapper";
	public static final String RESPONSE_PACKAGE = BASE_PACKAGE_PARENT + "common.bean.Response";
	public static final String BASE_PAGE_REQUEST_PACKAGE = BASE_PACKAGE_PARENT + "database.bean.PageRequest";
	public static final String BASE_PAGE_RESPONSE_PACKAGE = BASE_PACKAGE_PARENT + "common.bean.PageResponse";
	public static final String BASE_PAGE_MODEL_PACKAGE = BASE_PACKAGE_PARENT + "database.bean.PageModel";
	public static final String FREEMARKER_UTIL_PACKAGE = BASE_PACKAGE_PARENT + "freemarker.util.FreeMarkerUtil";
	public static final String SQLBUILDER_PACKAGE = BASE_PACKAGE_PARENT + "database.bean.SQLBuilder";
	public static final String SQLWHERE_PACKAGE = BASE_PACKAGE_PARENT + "database.bean.SQLWhere";
	public static final String SQL_WHERE_BUILDER_PACKAGE = BASE_PACKAGE_PARENT + "database.bean.SQLWhere.SQLWhereBuilder";
	public static final String SQLBUILDERS_PACKAGE = BASE_PACKAGE_PARENT + "database.bean.SQLBuilders";
	
	public static final String DAL_MODEL_PACKAGE_NAME = "model";
	public static final String SERVICE_PACKAGE = "service";
	public static final String CONTROLLER_PACKAGE = "controller";
	
	
	public static final String REQUEST_PACKAGE_NAME = "request";
	public static final String RESPONSE_PACKAGE_NAME = "response";
	public static final String IMPL_PACKAGE_NAME = "impl";
	public static final String CONVERT_PACKAGE_NAME = "convert";
	public static final String AUTHER_NAME = "huangping";
		
	public static List<String> tableList = new ArrayList<>();
	
	public static void main(AutoCreateBean bean) {
		if (CollectionUtils.isEmpty(bean.getTableNameList())) {
			log.error("auto create error. with table is empty.");
			return;
		}
		MAIN_PATH_DIR = bean.getMainPathDir();
		JAVA_DIR = bean.getJavaDir();
		PROJECT_PACKAGE = bean.getProjectPackage();
		SERVICE_MAVEN_MODULE = bean.getServiceMavenModule();
		CONTROLLER_MAVEN_MODULE = bean.getControllerMavenModule();
		MAPPING_DIR = bean.getMappingDir();
		WEB_MAVEN_MODULE_NAME = bean.getWebMavenModuleName();
		DAL_MAVEN_MODULE = bean.getDalMavenModule();
		COMMON_MAVEN_MODULE = bean.getCommonMavenModule();
		MODEL_MAVEN_MODULE = bean.getModelMavenModule();
		
		DAL_PACKAGE_NAME = bean.getDalPackageName();
		COMMON_PACKAGE_NAME = bean.getCommonPackageName();
		MODEL_PACKAGE_NAME = bean.getModelPackageName();
		SERVICE_PACKAGE_NAME = bean.getServicePackageName();
		CONTROLLER_PACKAGE_NAME = bean.getControllerPackageName();
		try {
			DataSource datasource = SpringContextUtil.getBean(DynamicDatasource.class);
			Connection conn = datasource.getConnection();
			TableBean table = null;
			
			Map<String, Object> map = null;
			for (String tableName : bean.getTableNameList()) {
				table = TableBeanHelper.getTableInfoByTableName(conn, tableName);
								
				map = getParamsMap(table);
				//生成model
				CreateDalModel.create(table, map);
				
				//生成dao
				CreateDAO.create(table, map);
				
				//生成mapper文件
				CreateMapper.create(table, map);
				
				if (bean.isCreateService()) {
					//生成request model
					CreatModel.creat(table, "1", map);
					
					//生成response model
					CreatModel.creat(table, "2", map);
					
					//生成convert
					CreateConvert.create(table, map);
					
					//生成service
					CreateService.createInterface(table, map);
					
					//生成实现类
					CreateService.createInterfaceImpl(table, map);
				}
				
				if (bean.isCreateController()) {
					//生成controller
					CreateController.create(table, map);
				}
				
				if (bean.isCreateFtl()) {
					CreateFTL.createFTLList(table);
					CreateFTL.createFTLEdit(table);
					CreateFTL.createFTLSearch(table);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成文件
	 * @param fileName
	 * @param data
	 */
	public static void saveFile(String fileName, String data) {
		try {
			String path = fileName.substring(0,  fileName.lastIndexOf("/"));
			File pathFile = new File(path);
			//文件夹不存在，则创建文件夹
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			FileUtils.writeStringToFile(new File(fileName), data, "UTF-8");
			log.info("createFile with fileName={}", fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* @Title: getParamsMap  
	* @Description: 获取公共的map
	* @param table
	* @return
	 */
	private static Map<String, Object> getParamsMap(TableBean table) {
		Map<String, Object> map = new HashMap<>();
		String dalPackage = CreateFile.PROJECT_PACKAGE + "." + CreateFile.DAL_PACKAGE_NAME;
		map.put("dalPackage", dalPackage);
		map.put("dalModelPackage", dalPackage + "." + CreateFile.DAL_MODEL_PACKAGE_NAME);
		map.put("modelName", table.getModelName());
		map.put("modelNameFirstLow", table.getModelNameFirstLow());
		map.put("author", AUTHER_NAME);
		map.put("responsePackage", CreateFile.RESPONSE_PACKAGE);
		map.put("baseBeanPackage", CreateFile.BASE_BEAN_PACKAGE);
		map.put("baseMapperPackage", CreateFile.BASE_MAPPER_PACKAGE);
		map.put("baseRequestBOPackage", CreateFile.BASE_REQUEST_BO_PACKAGE);
		map.put("baseResponseBOPackage", CreateFile.BASE_RESPONSE_BO_PACKAGE);
		map.put("requestModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_PACKAGE_NAME + "." + CreateFile.REQUEST_PACKAGE_NAME);
		map.put("responseModelPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.MODEL_PACKAGE_NAME + "." + CreateFile.RESPONSE_PACKAGE_NAME);
		map.put("pageModelPackage", CreateFile.BASE_PAGE_MODEL_PACKAGE);
		map.put("pageRequestPackage", CreateFile.BASE_PAGE_REQUEST_PACKAGE);
		map.put("pageResponsePackage", CreateFile.BASE_PAGE_RESPONSE_PACKAGE);
		map.put("servicePackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.SERVICE_PACKAGE_NAME + "." + CreateFile.SERVICE_PACKAGE);
		map.put("convertPackage", CreateFile.PROJECT_PACKAGE + "." + CreateFile.COMMON_PACKAGE_NAME + "." + CreateFile.CONVERT_PACKAGE_NAME);
		map.put("freeMarkerUtilPackage", CreateFile.FREEMARKER_UTIL_PACKAGE);
		
		map.put("SQLBuilderPackage", CreateFile.SQLBUILDER_PACKAGE);
		map.put("SQLBuildersPackage", CreateFile.SQLBUILDERS_PACKAGE);
		map.put("SQLWherePackage", CreateFile.SQLWHERE_PACKAGE);
		map.put("SQLWhereBuilderPackage", CreateFile.SQL_WHERE_BUILDER_PACKAGE);
		
		map.put("columnList", table.getColumnList());
		map.put("modelName", table.getModelName());
		map.put("primaryKey", table.getPrimaryKey());
		map.put("tableComment", table.getTableComment());
		return map;
	}

}
