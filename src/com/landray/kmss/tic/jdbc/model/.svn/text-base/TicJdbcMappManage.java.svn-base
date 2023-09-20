package com.landray.kmss.tic.jdbc.model;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.tic.jdbc.forms.TicJdbcMappManageForm;

/**
 * 映射配置
 * 
 * @author 
 * @version 1.0 2013-07-24
 */
public class TicJdbcMappManage extends BaseModel implements InterceptFieldEnabled {

	/**
	 * 映射名称
	 */
	protected String docSubject;
	
	/**
	 * @return 映射名称
	 */
	public String getDocSubject() {
		return docSubject;
	}
	
	/**
	 * @param docSubject 映射名称
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}
	
	/**
	 * 数据源
	 */
	protected String fdDataSource;
	
	/**
	 * @return 数据源
	 */
	public String getFdDataSource() {
		return fdDataSource;
	}
	
	/**
	 * @param fdDataSource 数据源
	 */
	public void setFdDataSource(String fdDataSource) {
		this.fdDataSource = fdDataSource;
	}
	
	/**
	 * 是否启用
	 */
	protected Boolean fdIsEnabled;
	
	/**
	 * @return 是否启用
	 */
	public Boolean getFdIsEnabled() {
		return fdIsEnabled;
	}
	
	/**
	 * @param fdIsEnabled 是否启用
	 */
	public void setFdIsEnabled(Boolean fdIsEnabled) {
		this.fdIsEnabled = fdIsEnabled;
	}
	
	/**
	 * 数据集
	 */
	protected TicJdbcDataSet ticJdbcDataSet;
	
	public TicJdbcDataSet getTicJdbcDataSet() {
		return ticJdbcDataSet;
	}

	public void setTicJdbcDataSet(TicJdbcDataSet ticJdbcDataSet) {
		this.ticJdbcDataSet = ticJdbcDataSet;
	}
	
	/**
	 * 数据集模版SQL
	 */
	protected String fdDataSetSql;
	
	public String getFdDataSetSql() {
		return fdDataSetSql;
	}

	public void setFdDataSetSql(String fdDataSetSql) {
		this.fdDataSetSql = fdDataSetSql;
	}

	/**
	 * 数据源SQL
	 */
	protected String fdDataSourceSql;
	
	/**
	 * @return 数据源SQL
	 */
	public String getFdDataSourceSql() {
		return fdDataSourceSql;
	}
	
	/**
	 * @param fdDataSourceSql 数据源SQL
	 */
	public void setFdDataSourceSql(String fdDataSourceSql) {
		this.fdDataSourceSql = fdDataSourceSql;
	}
	
	/**
	 * 目标数据源
	 */
	protected String fdTargetSource;
	
	/**
	 * @return 目标数据源
	 */
	public String getFdTargetSource() {
		return fdTargetSource;
	}
	
	/**
	 * @param fdTargetSource 目标数据源
	 */
	public void setFdTargetSource(String fdTargetSource) {
		this.fdTargetSource = fdTargetSource;
	}
	
	/**
	 * 目标数据库已选表
	 */
	protected String fdTargetSourceSelectedTable;
	
	/**
	 * @return 目标数据库已选表
	 */
	public String getFdTargetSourceSelectedTable() {
		return fdTargetSourceSelectedTable;
	}
	
	/**
	 * @param fdTargetSourceSelectedTable 目标数据库已选表
	 */
	public void setFdTargetSourceSelectedTable(String fdTargetSourceSelectedTable) {
		this.fdTargetSourceSelectedTable = fdTargetSourceSelectedTable;
	}
	
	/**
	 * 映射关系JSON
	 */
	protected String fdMappConfigJson;
	
	/**
	 * @return 映射关系JSON
	 */
	public String getFdMappConfigJson() {
		return (String) readLazyField("fdMappConfigJson", fdMappConfigJson);
	}
	
	/**
	 * @param fdMappConfigJson 映射关系JSON
	 */
	public void setFdMappConfigJson(String fdMappConfigJson) {
		this.fdMappConfigJson = (String) writeLazyField("fdMappConfigJson",
				this.fdMappConfigJson, fdMappConfigJson);
	}

	/**
	 * 所属分类
	 */
	protected TicJdbcMappCategory docCategory;
	
	/**
	 * @return 所属分类
	 */
	public TicJdbcMappCategory getDocCategory() {
		return docCategory;
	}
	
	/**
	 * @param docCategory 所属分类
	 */
	public void setDocCategory(TicJdbcMappCategory docCategory) {
		this.docCategory = docCategory;
	}
	
	@Override
    public Class getFormClass() {
		return TicJdbcMappManageForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCategory.fdId", "docCategoryId");
			toFormPropertyMap.put("docCategory.fdName", "docCategoryName");
			toFormPropertyMap.put("ticJdbcDataSet.fdId", "ticJdbcDataSetId");
			toFormPropertyMap.put("ticJdbcDataSet.docSubject", "ticJdbcDataSetName");
		}
		return toFormPropertyMap;
	}
}
