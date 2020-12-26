/**
 * 
 */
package com.hp.springboot.database.bean;

import java.util.List;

import javax.sql.DataSource;

import com.hp.springboot.common.bean.AbstractBean;

/**
 * @author huangping
 * 2018年4月2日
 */
public class DynamicDatasourceBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2845239041470232775L;

	
	private List<DataSource> masterDatasource;
	private List<DataSource> slaveDatasource;
	
	public DynamicDatasourceBean() {}

	public DynamicDatasourceBean(List<DataSource> masterDatasource) {
		this.masterDatasource = masterDatasource;
	}
	
	public DynamicDatasourceBean(List<DataSource> masterDatasource, List<DataSource> slaveDatasource) {
		this(masterDatasource);
		this.slaveDatasource = slaveDatasource;
	}
	public List<DataSource> getMasterDatasource() {
		return masterDatasource;
	}
	public void setMasterDatasource(List<DataSource> masterDatasource) {
		this.masterDatasource = masterDatasource;
	}
	public List<DataSource> getSlaveDatasource() {
		return slaveDatasource;
	}
	public void setSlaveDatasource(List<DataSource> slaveDatasource) {
		this.slaveDatasource = slaveDatasource;
	}
	
}
