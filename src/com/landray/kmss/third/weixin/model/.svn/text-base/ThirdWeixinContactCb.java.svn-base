package com.landray.kmss.third.weixin.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.third.weixin.forms.ThirdWeixinContactCbForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 客户回调日志
  */
public class ThirdWeixinContactCb extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdEventType;

    private String fdEventContent;

    private String fdRecordId;

    private String fdHandleResult;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdErrorMsg;

    private String fdCorpId;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinContactCbForm> getFormClass() {
        return ThirdWeixinContactCbForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 事件类型
     */
    public String getFdEventType() {
        return this.fdEventType;
    }

    /**
     * 事件类型
     */
    public void setFdEventType(String fdEventType) {
        this.fdEventType = fdEventType;
    }

    /**
     * 报文
     */
    public String getFdEventContent() {
        return (String) readLazyField("fdEventContent", this.fdEventContent);
    }

    /**
     * 报文
     */
    public void setFdEventContent(String fdEventContent) {
        this.fdEventContent = (String) writeLazyField("fdEventContent", this.fdEventContent, fdEventContent);
    }

    /**
     * 记录ID
     */
    public String getFdRecordId() {
        return this.fdRecordId;
    }

    /**
     * 记录ID
     */
    public void setFdRecordId(String fdRecordId) {
        this.fdRecordId = fdRecordId;
    }

    /**
     * 处理结果
     */
    public String getFdHandleResult() {
        return this.fdHandleResult;
    }

    /**
     * 处理结果
     */
    public void setFdHandleResult(String fdHandleResult) {
        this.fdHandleResult = fdHandleResult;
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
     * 更新时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 更新时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 错误信息
     */
    public String getFdErrorMsg() {
        return (String) readLazyField("fdErrorMsg", this.fdErrorMsg);
    }

    /**
     * 错误信息
     */
    public void setFdErrorMsg(String fdErrorMsg) {
        this.fdErrorMsg = (String) writeLazyField("fdErrorMsg", this.fdErrorMsg, fdErrorMsg);
    }

    /**
     * 微信CorpID
     */
    public String getFdCorpId() {
        return this.fdCorpId;
    }

    /**
     * 微信CorpID
     */
    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
