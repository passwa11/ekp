package com.landray.kmss.tic.rest.connector.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.rest.connector.forms.TicRestQueryForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

public class TicRestQuery extends BaseModel implements InterceptFieldEnabled {

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
	protected TicRestMain ticRestMain;

	public TicRestMain getTicRestMain() {
		return ticRestMain;
	}

	public void setTicRestMain(TicRestMain ticRestMain) {
		this.ticRestMain = ticRestMain;
	}

	/**
	 * 查询参数
	 */
	protected String fdQueryParam;

	public String getFdQueryParam() {
		return (String) readLazyField("fdQueryParam", fdQueryParam);
	}

	public void setFdQueryParam(String fdQueryParam) {
		this.fdQueryParam = (String) writeLazyField("fdQueryParam",
				this.fdQueryParam, fdQueryParam);
	}

	/**
	 * 错误信息
	 */
	protected String docFaultInfo;

	public String getDocFaultInfo() {
		return (String) readLazyField("docFaultInfo", docFaultInfo);
	}

	public void setDocFaultInfo(String docFaultInfo) {
		this.docFaultInfo = (String) writeLazyField("docFaultInfo",
				this.docFaultInfo, docFaultInfo);
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
			toFormPropertyMap.put("ticRestMain.fdId", "ticRestMainId");
			toFormPropertyMap.put("ticRestMain.fdName",
					"ticRestMainName");
		}
		return toFormPropertyMap;
	}

	@Override
	public Class getFormClass() {
		return TicRestQueryForm.class;
	}

}
