/**
 * 
 */
package com.hp.springboot.database.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.hp.springboot.common.bean.AbstractBean;

/**
 * @author huangping
 * 2016年8月25日 下午11:58:19
 */
public class PageModel extends AbstractBean {

	private static final long serialVersionUID = -6321131810635063357L;

	/**
	 * 页码，默认是第一页
	 */
	private int currentPage = 1;
	
	/**
	 * 每页显示的记录数，默认是10
	 */
	private int pageSize = 10;
	
	/**
	 * 查询的开始行数
	 */
	private int startIndex = 0;
	
	/**
	 * 排序
	 */
	private List<OrderBy> orderBy;
	
	public PageModel() {}
	
	public PageModel(int currentPage, int pageSize) {
		setCurrentPage(currentPage);
		setPageSize(pageSize);
	}
	
	public PageModel(int currentPage, int pageSize, OrderBy... orderBy) {
		this(currentPage, pageSize);
		if (ArrayUtils.isEmpty(orderBy)) {
			return;
		}
		for (OrderBy o : orderBy) {
			addOrderBy(o);
		}
	}

	public PageModel(int currentPage, int pageSize, String sortColumn) {
		this(currentPage, pageSize, OrderBy.of(sortColumn));
	}
	
	public static PageModel of(int currentPage, int pageSize, OrderBy... orderBy) {
		return new PageModel(currentPage, pageSize, orderBy);
	}
	
	public static PageModel of(int currentPage, int pageSize, String sortColumn) {
		return new PageModel(currentPage, pageSize, sortColumn);
	}
	
	public static PageModel emptyPage() {
		return PageModel.of(0, 0);
	}

	public int getPageSize() {
		return pageSize;
	}

	public PageModel setPageSize(int pageSize) {
		this.pageSize = pageSize;
		setStartIndex((this.currentPage - 1) * this.pageSize);
		return this;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public PageModel setStartIndex(int startIndex) {
		this.startIndex = startIndex;
		return this;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public PageModel setCurrentPage(int currentPage) {
		if (currentPage <= 0) {
			this.currentPage = 1;
		} else {
			this.currentPage = currentPage;
		}
		setStartIndex((this.currentPage - 1) * this.pageSize);
		return this;
	}

	public List<OrderBy> getOrderBy() {
		return orderBy;
	}

	public PageModel setOrderBy(List<OrderBy> orderBy) {
		this.orderBy = orderBy;
		return this;
	}
	
	public PageModel addOrderBy(OrderBy... orderBy) {
		initOrderBy();
		if (ArrayUtils.isEmpty(orderBy) || orderBy[0] == null) {
			return this;
		}
		this.orderBy.addAll(Arrays.asList(orderBy));
		return this;
	}
	
	private void initOrderBy() {
		if (this.orderBy == null) {
			this.orderBy = new ArrayList<>();
		}
	}
}
