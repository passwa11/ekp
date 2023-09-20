package com.landray.kmss.tic.jdbc.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.tic.core.common.model.TicCoreFuncBase;
import com.landray.kmss.tic.jdbc.forms.TicJdbcDataSetForm;

/**
 * 数据集管理
 * 
 * @author
 * @version 1.0 2014-04-15
 */
public class TicJdbcDataSet extends TicCoreFuncBase {

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
	 * @param fdDataSource
	 *            数据源
	 */
	public void setFdDataSource(String fdDataSource) {
		this.fdDataSource = fdDataSource;
	}

	/**
	 * SQL语句
	 */
	protected String fdSqlExpression;

	/**
	 * @return SQL语句
	 */
	public String getFdSqlExpression() {
		return fdSqlExpression;
	}

	/**
	 * @param fdSqlExpression
	 *            SQL语句
	 */
	public void setFdSqlExpression(String fdSqlExpression) {
		this.fdSqlExpression = fdSqlExpression;
	}

	/**
	 * 配置数据
	 */
	protected String fdData;

	/**
	 * @return 配置数据
	 */
	public String getFdData() {
		return (String) readLazyField("fdData", fdData);
	}

	/**
	 * @param fdData
	 *            配置数据
	 */
	public void setFdData(String fdData) {
		this.fdData = (String) writeLazyField("fdData", this.fdData, fdData);
	}

	@Override
    public Class getFormClass() {
		return TicJdbcDataSetForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

		}
		return toFormPropertyMap;
	}

	protected String fdSqlExpressionTest;

	public String getFdSqlExpressionTest() {
		return fdSqlExpressionTest;
	}

	public void setFdSqlExpressionTest(String fdSqlExpressionTest) {
		this.fdSqlExpressionTest = fdSqlExpressionTest;
	}
}
