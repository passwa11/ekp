package com.landray.kmss.km.review.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.review.model.KmReviewFeedbackInfo;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.BaseAuthForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

/**
 * 创建日期 2007-Aug-30
 * 
 * @author 舒斌
 */
public class KmReviewFeedbackInfoForm extends BaseAuthForm
		implements IAttachmentForm

{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -781110232694790189L;

	/*
	 * 主文档ID
	 */
	private String fdMainId = null;

	/*
	 * 创建人
	 */
	private String docCreatorName = null;

	private String docCreatorId = null;

	/*
	 * 通知人
	 */
	private String fdNotifyId = null;
	private String fdNotifyPeople = null;

	/*
	 * 通知类型
	 */
	private String fdNotifyType = null;

	/*
	 * 提要
	 */
	private String fdSummary = null;

	/*
	 * 反馈时间
	 */
	private String docCreatorTime = null;

	/*
	 * 反馈内容
	 */
	private String docContent = null;
	
	private String fdReaderNames = null;

	/**
	 * @return 返回 提要
	 */
	public String getFdSummary() {
		return fdSummary;
	}

	/**
	 * @param fdSummary
	 *            要设置的 提要
	 */
	public void setFdSummary(String fdSummary) {
		this.fdSummary = fdSummary;
	}

	/**
	 * @return 返回 反馈时间
	 */
	public String getDocCreatorTime() {
		return docCreatorTime;
	}

	/**
	 * @param docCreatorTime
	 *            要设置的 反馈时间
	 */
	public void setDocCreatorTime(String docCreatorTime) {
		this.docCreatorTime = docCreatorTime;
	}

	/**
	 * @return 返回 反馈内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            要设置的 反馈内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.landray.kmss.web.action.ActionForm#reset(com.landray.kmss.web.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMainId = null;
		fdNotifyId = null;
		fdHasAttachment = null;
		docCreatorId = null;
		docCreatorName = null;
		fdSummary = null;
		docCreatorTime = null;
		docContent = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmReviewFeedbackInfo.class;
	}

	public String getDocCreatorId() {
		return docCreatorId;
	}

	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}



	public String getFdMainId() {
		return fdMainId;
	}

	public void setFdMainId(String fdMainId) {
		this.fdMainId = fdMainId;
	}

	public String getFdNotifyPeople() {
		return fdNotifyPeople;
	}

	public void setFdNotifyPeople(String fdNotifyPeople) {
		this.fdNotifyPeople = fdNotifyPeople;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	private static FormToModelPropertyMap formToModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (formToModelPropertyMap == null) {
			formToModelPropertyMap = new FormToModelPropertyMap();
			formToModelPropertyMap.putAll(super.getToModelPropertyMap());
			// 时间
			formToModelPropertyMap.put("docCreatorTime",
					new FormConvertor_Common("docCreateTime")
							.setDateTimeType(DateUtil.TYPE_DATETIME));
			// 创建人
			formToModelPropertyMap.put("docCreatorId",
					new FormConvertor_IDToModel("fdCreator",
							SysOrgElement.class));
			//文档
			formToModelPropertyMap.put("fdMainId",
					new FormConvertor_IDToModel("kmReviewMain",
							KmReviewMain.class));

		}
		return formToModelPropertyMap;
	}

	public String getFdNotifyId() {
		return fdNotifyId;
	}

	public void setFdNotifyId(String fdNotifyId) {
		this.fdNotifyId = fdNotifyId;
	}

	public String getFdReaderNames() {
		return fdReaderNames;
	}

	public void setFdReaderNames(String fdReaderNames) {
		this.fdReaderNames = fdReaderNames;
	}

	/**
	 * 是否有附件
	 */
	protected String fdHasAttachment = null;

	/**
	 * @return 是否有附件
	 */
	public String getFdHasAttachment() {
		return fdHasAttachment;
	}

	/**
	 * @param fdHasAttachment
	 *            是否有附件
	 */
	public void setFdHasAttachment(String fdHasAttachment) {
		this.fdHasAttachment = fdHasAttachment;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

}
