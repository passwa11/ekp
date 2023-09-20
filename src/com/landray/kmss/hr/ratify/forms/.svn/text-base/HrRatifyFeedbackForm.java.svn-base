package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.ratify.model.HrRatifyFeedback;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 流程反馈
  */
public class HrRatifyFeedbackForm extends ExtendForm
		implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSummary;

    private String docCreateTime;

    private String docContent;

	private String fdNotifyId = null;

    private String fdNotifyPeople;

    private String fdNotifyType;

    private String docCreatorId;

    private String docCreatorName;

    private String fdDocId;

    private String fdDocName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdSummary = null;
        docCreateTime = null;
        docContent = null;
		fdNotifyId = null;
        fdNotifyPeople = null;
        fdNotifyType = null;
        docCreatorId = null;
        docCreatorName = null;
        fdDocId = null;
        fdDocName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<HrRatifyFeedback> getModelClass() {
        return HrRatifyFeedback.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdDocId", new FormConvertor_IDToModel("fdDoc", HrRatifyMain.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 提要
     */
    public String getFdSummary() {
        return this.fdSummary;
    }

    /**
     * 提要
     */
    public void setFdSummary(String fdSummary) {
        this.fdSummary = fdSummary;
    }

    /**
     * 反馈时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 反馈时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 反馈内容
     */
    public String getDocContent() {
        return this.docContent;
    }

    /**
     * 反馈内容
     */
    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

	/**
	 * 通知人员
	 */
	public String getFdNotifyId() {
		return fdNotifyId;
	}

	/**
	 * 通知人员
	 */
	public void setFdNotifyId(String fdNotifyId) {
		this.fdNotifyId = fdNotifyId;
	}

    /**
     * 通知人员
     */
    public String getFdNotifyPeople() {
        return this.fdNotifyPeople;
    }

    /**
     * 通知人员
     */
    public void setFdNotifyPeople(String fdNotifyPeople) {
        this.fdNotifyPeople = fdNotifyPeople;
    }

    /**
     * 通知方式
     */
    public String getFdNotifyType() {
        return this.fdNotifyType;
    }

    /**
     * 通知方式
     */
    public void setFdNotifyType(String fdNotifyType) {
        this.fdNotifyType = fdNotifyType;
    }

    /**
     * 反馈人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 反馈人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 反馈人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 反馈人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 人事流程文档信息
     */
    public String getFdDocId() {
        return this.fdDocId;
    }

    /**
     * 人事流程文档信息
     */
    public void setFdDocId(String fdDocId) {
        this.fdDocId = fdDocId;
    }

    /**
     * 人事流程文档信息
     */
    public String getFdDocName() {
        return this.fdDocName;
    }

    /**
     * 人事流程文档信息
     */
    public void setFdDocName(String fdDocName) {
        this.fdDocName = fdDocName;
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
