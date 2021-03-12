/**
 * 
 */
package com.hp.springboot.mybatis.autocreate.helper;

import java.util.List;

import com.hp.springboot.common.bean.AbstractBean;


/**
 * @author huangping
 * 2018年9月19日
 */
public class AutoCreateBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9054161024845693362L;

	private List<String> tableNameList;
	private String mainPathDir = "./";//文件生成的主路径
	private String javaDir = "src/main/java";//java文件存放主目录
	private String serviceMavenModule = "service";//service所在项目名称
	private String controllerMavenModule = "controller";//controller所在项目名称
	private String projectPackage = "com.yoho.none";//DAO生成的包地址
	private String mappingDir = "src/main/resources/META-INF/mybatis";
	private String webMavenModuleName = "start";//启动的项目名
	private String dalMavenModule = "dal";//dao所在的子项目名称
	private String commonMavenModule = "common";//common所在的子项目名称
	private String modelMavenModule = "model";//model所在的子项目名称
	
	private String daoPackageName = "dal";
	private String commonPackageName = "common";
	private String modelPackageName = "model";
	private String servicePackageName = "service";
	private String controllerPackageName = "controller";
	
    
    private boolean createService = false;
    private boolean createController = false;
    private boolean createFtl = false;
    
	public List<String> getTableNameList() {
		return tableNameList;
	}
	public void setTableNameList(List<String> tableNameList) {
		this.tableNameList = tableNameList;
	}
	public String getMainPathDir() {
		return mainPathDir;
	}
	public void setMainPathDir(String mainPathDir) {
		this.mainPathDir = mainPathDir;
	}
	public String getJavaDir() {
		return javaDir;
	}
	public void setJavaDir(String javaDir) {
		this.javaDir = javaDir;
	}
	public String getServiceMavenModule() {
		return serviceMavenModule;
	}
	public void setServiceMavenModule(String serviceMavenModule) {
		this.serviceMavenModule = serviceMavenModule;
	}
	public String getControllerMavenModule() {
		return controllerMavenModule;
	}
	public void setControllerMavenModule(String controllerMavenModule) {
		this.controllerMavenModule = controllerMavenModule;
	}
	public String getProjectPackage() {
		return projectPackage;
	}
	public void setProjectPackage(String projectPackage) {
		this.projectPackage = projectPackage;
	}
	public String getMappingDir() {
		return mappingDir;
	}
	public void setMappingDir(String mappingDir) {
		this.mappingDir = mappingDir;
	}
	public boolean isCreateService() {
		return createService;
	}
	public void setCreateService(boolean createService) {
		this.createService = createService;
	}
	public boolean isCreateController() {
		return createController;
	}
	public void setCreateController(boolean createController) {
		this.createController = createController;
	}
	public boolean isCreateFtl() {
		return createFtl;
	}
	public void setCreateFtl(boolean createFtl) {
		this.createFtl = createFtl;
	}
	public String getWebMavenModuleName() {
		return webMavenModuleName;
	}
	public void setWebMavenModuleName(String webMavenModuleName) {
		this.webMavenModuleName = webMavenModuleName;
	}
	public String getCommonMavenModule() {
		return commonMavenModule;
	}
	public void setCommonMavenModule(String commonMavenModule) {
		this.commonMavenModule = commonMavenModule;
	}
	public String getModelMavenModule() {
		return modelMavenModule;
	}
	public void setModelMavenModule(String modelMavenModule) {
		this.modelMavenModule = modelMavenModule;
	}
	public String getDaoPackageName() {
		return daoPackageName;
	}
	public void setDaoPackageName(String daoPackageName) {
		this.daoPackageName = daoPackageName;
	}
	public String getCommonPackageName() {
		return commonPackageName;
	}
	public void setCommonPackageName(String commonPackageName) {
		this.commonPackageName = commonPackageName;
	}
	public String getModelPackageName() {
		return modelPackageName;
	}
	public void setModelPackageName(String modelPackageName) {
		this.modelPackageName = modelPackageName;
	}
	public String getServicePackageName() {
		return servicePackageName;
	}
	public void setServicePackageName(String servicePackageName) {
		this.servicePackageName = servicePackageName;
	}
	public String getControllerPackageName() {
		return controllerPackageName;
	}
	public void setControllerPackageName(String controllerPackageName) {
		this.controllerPackageName = controllerPackageName;
	}
	public String getDalMavenModule() {
		return dalMavenModule;
	}
	public void setDalMavenModule(String dalMavenModule) {
		this.dalMavenModule = dalMavenModule;
	}
    
}
