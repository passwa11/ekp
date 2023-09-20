package com.landray.kmss.hr.ratify.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyFeedbackForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 流程反馈
  */
public class HrRatifyFeedback extends BaseModel
		implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdSummary;

    private Date docCreateTime;

    private String docContent;

    private String fdNotifyPeople;

    private String fdNotifyType;

    private SysOrgPerson docCreator;

	private HrRatifyMain fdDoc = new HrRatifyMain();

    @Override
    public Class<HrRatifyFeedbackForm> getFormClass() {
        return HrRatifyFeedbackForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdDoc.docSubject", "fdDocName");
            toFormPropertyMap.put("fdDoc.fdId", "fdDocId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 反馈时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 反馈内容
     */
    public String getDocContent() {
        return (String) readLazyField("docContent", this.docContent);
    }

    /**
     * 反馈内容
     */
    public void setDocContent(String docContent) {
        this.docContent = (String) writeLazyField("docContent", this.docContent, docContent);
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
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 反馈人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 人事流程文档信息
     */
    public HrRatifyMain getFdDoc() {
		// return this.fdDoc;
		return (HrRatifyMain) readLazyField("fdDoc", this.fdDoc);
    }

    /**
     * 人事流程文档信息
     */
    public void setFdDoc(HrRatifyMain fdDoc) {
		// this.fdDoc = fdDoc;
		this.fdDoc = (HrRatifyMain) writeLazyField("fdDoc", this.fdDoc, fdDoc);
    }

	/**
	 * 是否有附件
	 */
	protected Boolean fdHasAttachment;

	/**
	 * @return 是否有附件
	 */
	public Boolean getFdHasAttachment() {
		return fdHasAttachment;
	}

	/**
	 * @param fdHasAttachment
	 *            是否有附件
	 */
	public void setFdHasAttachment(Boolean fdHasAttachment) {
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
