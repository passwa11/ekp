package com.landray.kmss.km.forum.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.forum.model.KmForumTopic;

/**
 * 创建日期 2006-Sep-05
 * 
 * @author 吴兵
 */
public class KmForumTopicForm extends ExtendForm {
	/*
	 * 版块ID
	 */
	private String fdForumId = null;
	private String fdForumName = null;
	private String fdStatus = null;
	private String fdIsNotify = null;
	private String fdNotifyType = null;
	private String docSubject = null;
	private String fdTargetIds = null;
	private String fdTargetNames = null;

	public String getFdTargetIds() {
		return fdTargetIds;
	}

	public void setFdTargetIds(String fdTargetIds) {
		this.fdTargetIds = fdTargetIds;
	}

	public String getFdTargetNames() {
		return fdTargetNames;
	}

	public void setFdTargetNames(String fdTargetNames) {
		this.fdTargetNames = fdTargetNames;
	}

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * @return 返回 版块ID
	 */
	public String getFdForumId() {
		return fdForumId;
	}

	public String getFdForumName() {
		return fdForumName;
	}

	/**
	 * @param fdTopicId
	 *            要设置的 版块ID
	 */
	public void setFdForumId(String fdForumId) {
		this.fdForumId = fdForumId;
	}

	public void setFdForumName(String fdForumName) {
		this.fdForumName = fdForumName;
	}

	public String getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdForumId = null;
		fdForumName = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmForumTopic.class;
	}

	public String getFdIsNotify() {
		return fdIsNotify;
	}

	public void setFdIsNotify(String fdIsNotify) {
		this.fdIsNotify = fdIsNotify;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

}
