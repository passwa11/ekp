package com.landray.kmss.sys.praise.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.BaseCoreInnerForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.praise.model.SysPraiseMain;

public class SysPraiseMainForm extends BaseCoreInnerForm{
	/*
	 * 点赞人id
	 */
	protected String fdPraisePersonId = null;
	public String getFdPraisePersonId() {
		return fdPraisePersonId;
	}

	public void setFdPraisePersonId(String fdPraisePersonId) {
		this.fdPraisePersonId = fdPraisePersonId;
	}

	public String getFdPraisePersonName() {
		return fdPraisePersonName;
	}

	public void setFdPraisePersonName(String fdPraisePersonName) {
		this.fdPraisePersonName = fdPraisePersonName;
	}

	/*
	 * 点赞人name
	 */
	protected String fdPraisePersonName = null;
	
	/*
	 * 点赞时间
	 */
	private String fdPraiseTime = null;
	/*
	 * 被点赞文档标题
	 */
	private String docSubject = null;
	public String getFdPraiseTime() {
		return fdPraiseTime;
	}

	public void setFdPraiseTime(String fdPraiseTime) {
		this.fdPraiseTime = fdPraiseTime;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	
	/**
	 * 模块ID
	 */
	protected String fdModelId = null;

	/**
	 * @return 模块ID
	 */
	@Override
    public String getFdModelId() {
		return fdModelId;
	}

	/**
	 * @param fdModelId
	 *            模块ID
	 */
	@Override
    public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	/**
	 * 模块名
	 */
	protected String fdModelName = null;

	/**
	 * @return 模块名
	 */
	@Override
    public String getFdModelName() {
		return fdModelName;
	}

	/**
	 * @param fdModelName
	 *            模块名
	 */
	@Override
    public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	
	public String fdType=null;
	
	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdPraisePersonId = null;
		fdPraisePersonName = null;
		fdPraiseTime = null;
		docSubject = null;
		fdModelId = null;
		fdModelName = null;
		fdType=null;
		super.reset(mapping, request);
	}
	@Override
	public Class getModelClass() {
		return SysPraiseMain.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPraisePersonId",
					new FormConvertor_IDToModel("fdPraisePerson",
						SysOrgElement.class));
		}
		return toModelPropertyMap;
	}
}
