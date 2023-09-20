package com.landray.kmss.tic.soap.connector.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.tic.soap.connector.model.TicSoapMain;
import com.landray.kmss.tic.soap.connector.model.TicSoapQuery;
import com.landray.kmss.web.action.ActionMapping;


/**
 * 函数查询 Form
 * 
 * @author 
 * @version 1.0 2012-08-28
 */
public class TicSoapQueryForm extends ExtendForm {

	/**
	 * 函数查询标题
	 */
	protected String docSubject = null;
	
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
	protected String docCreateTime = null;
	
	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}
	
	/**
	 * @param docCreateTime 创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}
	
	/**
	 * 输入参数
	 */
	protected String docInputParam = null;
	
	/**
	 * @return 输入参数
	 */
	public String getDocInputParam() {
		return docInputParam;
	}
	
	/**
	 * @param docInputParam 输入参数
	 */
	public void setDocInputParam(String docInputParam) {
		this.docInputParam = docInputParam;
	}
	
	/**
	 * 输出参数
	 */
	protected String docOutputParam = null;
	
	/**
	 * @return 输出参数
	 */
	public String getDocOutputParam() {
		return docOutputParam;
	}
	
	/**
	 * @param docOutputParam 输出参数
	 */
	public void setDocOutputParam(String docOutputParam) {
		this.docOutputParam = docOutputParam;
	}
	
	/**
	 * 错误信息
	 */
	protected String docFaultInfo;
	
	public String getDocFaultInfo() {
		return docFaultInfo;
	}

	public void setDocFaultInfo(String docFaultInfo) {
		this.docFaultInfo = docFaultInfo;
	}
	
	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;
	
	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}
	
	/**
	 * @param docCreatorId 创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}
	
	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;
	
	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}
	
	/**
	 * @param docCreatorName 创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}
	
	/**
	 * 所属函数的ID
	 */
	protected String ticSoapMainId = null;
	
	
	/**
	 * 所属函数的名称
	 */
	protected String ticSoapMainName = null;
	
	
	public String getTicSoapMainId() {
		return ticSoapMainId;
	}

	public void setTicSoapMainId(String ticSoapMainId) {
		this.ticSoapMainId = ticSoapMainId;
	}

	public String getTicSoapMainName() {
		return ticSoapMainName;
	}

	public void setTicSoapMainName(String ticSoapMainName) {
		this.ticSoapMainName = ticSoapMainName;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		docCreateTime = null;
		docInputParam = null;
		docOutputParam = null;
		docFaultInfo = null;
		docCreatorId = null;
		docCreatorName = null;
		ticSoapMainId = null;
		ticSoapMainName = null;
		
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return TicSoapQuery.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
						SysOrgElement.class));
			toModelPropertyMap.put("ticSoapMainId",
					new FormConvertor_IDToModel("ticSoapMain",
						TicSoapMain.class));
		}
		return toModelPropertyMap;
	}
	
	protected String fdJsonResult = null;

	public String getFdJsonResult() {
		return fdJsonResult;
	}

	public void setFdJsonResult(String fdJsonResult) {
		this.fdJsonResult = fdJsonResult;
	}
	
	public String getDocSubjectShow() {
		if (docSubject != null) {
			return docSubject.replaceAll("\"", "&quot;");
		}
		return docSubject;
	}

}
