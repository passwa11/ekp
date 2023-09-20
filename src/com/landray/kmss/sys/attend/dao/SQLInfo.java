package com.landray.kmss.sys.attend.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.Type;

/**
 * sql语句的信息描述
 *
 * @author cuiwj
 * @version 1.0 2019-02-23
 */
public class SQLInfo {

	private String fromBlock = null;

	private boolean getCount = true;

	private boolean gettingCount = false;

	private String joinBlock = null;

	private String tableName = null;

	private String orderBy = null;

	private int pageNo = 1;

	private List<SQLParameter> parameterList = new ArrayList<SQLParameter>();

	private int rowSize = 12;

	private String selectBlock = null;

	private String whereBlock = null;

	private boolean cacheable = false;

	private Class<?> EntityClass;

	public Class<?> getEntityClass() {
		return EntityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		EntityClass = entityClass;
	}

	public String getFromBlock() {
		return fromBlock;
	}

	public void setFromBlock(String fromBlock) {
		this.fromBlock = fromBlock;
	}

	public boolean isGetCount() {
		return getCount;
	}

	public void setGetCount(boolean getCount) {
		this.getCount = getCount;
	}

	public boolean isGettingCount() {
		return gettingCount;
	}

	public void setGettingCount(boolean gettingCount) {
		this.gettingCount = gettingCount;
	}

	public String getJoinBlock() {
		return joinBlock;
	}

	public void setJoinBlock(String joinBlock) {
		this.joinBlock = joinBlock;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<SQLParameter> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<SQLParameter> parameterList) {
		this.parameterList = parameterList;
	}

	public void setParameter(String key, Object value) {
		parameterList.add(new SQLParameter(key, value));
	}

	public void setParameter(String key, Object value, Type type) {
		parameterList.add(new SQLParameter(key, value, type));
	}

	public void setParameter(List<SQLParameter> parameterList) {
		this.parameterList.addAll(parameterList);
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public String getSelectBlock() {
		return selectBlock;
	}

	public void setSelectBlock(String selectBlock) {
		this.selectBlock = selectBlock;
	}

	public String getWhereBlock() {
		return whereBlock;
	}

	public void setWhereBlock(String whereBlock) {
		this.whereBlock = whereBlock;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

}
