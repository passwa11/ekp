package com.landray.kmss.tic.jdbc.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.tic.core.common.forms.TicCoreFuncBaseForm;
import com.landray.kmss.tic.jdbc.model.TicJdbcDataSet;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 数据集管理 Form
 * 
 * @author
 * @version 1.0 2014-04-15
 */
public class TicJdbcDataSetForm extends TicCoreFuncBaseForm {



	/* 排序号 */
	protected String fdOrder = null;

	public String getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 数据源
	 */
	protected String fdDataSource = null;

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
	protected String fdSqlExpression = null;

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
	protected String fdData = null;

	/**
	 * @return 配置数据
	 */
	public String getFdData() {
		return fdData;
	}

	/**
	 * @param fdData
	 *            配置数据
	 */
	public void setFdData(String fdData) {
		this.fdData = fdData;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdDataSource = null;
		fdSqlExpression = null;
		fdData = null;
		fdSqlExpressionTest = null;
		fdOrder = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return TicJdbcDataSet.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());

		}
		return toModelPropertyMap;
	}

	protected String fdSqlExpressionTest = null;

	public String getFdSqlExpressionTest() {
		return fdSqlExpressionTest;
	}

	public void setFdSqlExpressionTest(String fdSqlExpressionTest) {
		this.fdSqlExpressionTest = fdSqlExpressionTest;
	}
}
