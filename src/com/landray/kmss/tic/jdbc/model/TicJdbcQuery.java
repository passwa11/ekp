package com.landray.kmss.tic.jdbc.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.jdbc.forms.TicJdbcQueryForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

public class TicJdbcQuery extends BaseModel implements InterceptFieldEnabled {
	/**
	 * 函数查询标题
	 */
	protected String docSubject;

	/**
	 * @return 函数查询标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            函数查询标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 创建时间
	 */
	protected Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 创建者
	 */
	protected SysOrgElement docCreator;

	/**
	 * @return 创建者
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 所属函数
	 */
	protected TicJdbcDataSet ticJdbcDataSet;

	public TicJdbcDataSet getTicJdbcDataSet() {
		return ticJdbcDataSet;
	}

	public void setTicJdbcDataSet(TicJdbcDataSet ticJdbcDataSet) {
		this.ticJdbcDataSet = ticJdbcDataSet;
	}

	protected String fdJsonResult;

	public String getFdJsonResult() {
		return fdJsonResult;
	}

	public void setFdJsonResult(String fdJsonResult) {
		this.fdJsonResult = fdJsonResult;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;
	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("ticJdbcDataSet.fdId", "ticJdbcDataSetId");
			toFormPropertyMap.put("ticJdbcDataSet.fdName",
					"ticJdbcDataSetName");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		// TODO Auto-generated method stub
		return TicJdbcQueryForm.class;
	}
}
