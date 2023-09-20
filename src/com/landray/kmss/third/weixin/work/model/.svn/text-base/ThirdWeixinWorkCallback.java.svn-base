package com.landray.kmss.third.weixin.work.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinWorkCallbackForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import net.sf.cglib.transform.impl.InterceptFieldCallback;
import net.sf.cglib.transform.impl.InterceptFieldEnabled;

/**
  * 企业微信回调
  */
public class ThirdWeixinWorkCallback extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdEventType;

    private String fdEventTypeTip;

    private Long fdEventTime;

    private String docContent;

    private Boolean fdIsSuccess;

    private Date docCreateTime;

    private String fdErrorInfo;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinWorkCallbackForm> getFormClass() {
        return ThirdWeixinWorkCallbackForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 回调事件
     */
    public String getFdEventType() {
        return this.fdEventType;
    }

    /**
     * 回调事件
     */
    public void setFdEventType(String fdEventType) {
        this.fdEventType = fdEventType;
    }

    /**
     * 事件说明
     */
    public String getFdEventTypeTip() {
        return this.fdEventTypeTip;
    }

    /**
     * 事件说明
     */
    public void setFdEventTypeTip(String fdEventTypeTip) {
        this.fdEventTypeTip = fdEventTypeTip;
    }

    /**
     * 回调时间
     */
    public Long getFdEventTime() {
        return this.fdEventTime;
    }

    /**
     * 回调时间
     */
    public void setFdEventTime(Long fdEventTime) {
        this.fdEventTime = fdEventTime;
    }

    /**
     * 回调内容
     */
    public String getDocContent() {
        return (String) readLazyField("docContent", this.docContent);
    }

    /**
     * 回调内容
     */
    public void setDocContent(String docContent) {
        this.docContent = (String) writeLazyField("docContent", this.docContent, docContent);
    }

    /**
     * 状态
     */
    public Boolean getFdIsSuccess() {
        return this.fdIsSuccess;
    }

    /**
     * 状态
     */
    public void setFdIsSuccess(Boolean fdIsSuccess) {
        this.fdIsSuccess = fdIsSuccess;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 错误信息
     */
    public String getFdErrorInfo() {
        return (String) readLazyField("fdErrorInfo", this.fdErrorInfo);
    }

    /**
     * 错误信息
     */
    public void setFdErrorInfo(String fdErrorInfo) {
        this.fdErrorInfo = (String) writeLazyField("fdErrorInfo", this.fdErrorInfo, fdErrorInfo);
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

	@Override
	public InterceptFieldCallback getInterceptFieldCallback() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInterceptFieldCallback(InterceptFieldCallback arg0) {
		// TODO Auto-generated method stub

	}
}
