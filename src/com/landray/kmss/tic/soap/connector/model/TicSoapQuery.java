package com.landray.kmss.tic.soap.connector.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.soap.connector.forms.TicSoapQueryForm;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 函数查询
 * 
 * @author 
 * @version 1.0 2012-08-28
 */
public class TicSoapQuery extends BaseModel implements InterceptFieldEnabled {

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
	 * @param docSubject 函数查询标题
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
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 输入参数
	 */
	protected String docInputParam;
	
	/**
	 * @return 输入参数
	 */
	public String getDocInputParam() {
		return (String) readLazyField("docInputParam", docInputParam);
	}
	
	/**
	 * @param docInputParam 输入参数
	 */
	public void setDocInputParam(String docInputParam) {
		this.docInputParam = (String) writeLazyField("docInputParam",
				this.docInputParam, docInputParam);
	}

	/**
	 * 输出参数
	 */
	protected String docOutputParam;
	
	/**
	 * @return 输出参数
	 */
	public String getDocOutputParam() {
		return (String) readLazyField("docOutputParam", docOutputParam);
	}
	
	/**
	 * @param docOutputParam 输出参数
	 */
	public void setDocOutputParam(String docOutputParam) {
		this.docOutputParam = (String) writeLazyField("docOutputParam",
				this.docOutputParam, docOutputParam);
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
	 * @param docCreator 创建者
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}
	
	/**
	 * 所属函数
	 */
	protected TicSoapMain ticSoapMain;
	
	
	
	public TicSoapMain getTicSoapMain() {
		return ticSoapMain;
	}

	public void setTicSoapMain(TicSoapMain ticSoapMain) {
		this.ticSoapMain = ticSoapMain;
	}

	@Override
    public Class getFormClass() {
		return TicSoapQueryForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("ticSoapMain.fdId", "ticSoapMainId");
			toFormPropertyMap.put("ticSoapMain.fdName", "ticSoapMainName");
		}
		return toFormPropertyMap;
	}
	
	protected String fdJsonResult;

	public String getFdJsonResult() {
		return fdJsonResult;
	}

	public void setFdJsonResult(String fdJsonResult) {
		this.fdJsonResult = fdJsonResult;
	}
}
