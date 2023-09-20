package com.landray.kmss.sys.portal.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.portal.model.SysPortalNotice;

/**
 * @author linxiuxian
 *
 */
public class SysPortalNoticeForm extends ExtendForm {
	private String docContent;

	private String docStartTime;

	private String docEndTime;

	private String docCreatorId;

	private String docCreatorName;

	private String docCreateTime;

	private String fdState;

	public String getFdState() {
		return fdState;
	}

	public void setFdState(String fdState) {
		this.fdState = fdState;
	}

	public String getDocContent() {
		return docContent;
	}

	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	public String getDocStartTime() {
		return docStartTime;
	}

	public void setDocStartTime(String docStartTime) {
		this.docStartTime = docStartTime;
	}

	public String getDocEndTime() {
		return docEndTime;
	}

	public void setDocEndTime(String docEndTime) {
		this.docEndTime = docEndTime;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	@Override
	public Class getModelClass() {
		return SysPortalNotice.class;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		docContent = null;
		docCreateTime = null;
		docCreatorName = null;
		docCreatorId = null;
		docCreateTime = null;
		docEndTime = null;
		docStartTime = null;
		fdState = null;
		super.reset(mapping, request);
	}

	FormToModelPropertyMap formToModelPropertyMap = null;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			// 创建者
			formToModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("docCreator",
							SysOrgPerson.class));
		}
		return formToModelPropertyMap;
	}
}
