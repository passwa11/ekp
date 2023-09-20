package com.landray.kmss.third.ekp.java.notify.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.ekp.java.notify.forms.ThirdEkpJavaNotifyMappForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 待办映射
  */
public class ThirdEkpJavaNotifyMapp extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Date docCreateTime;

    private String fdNotifyId;

    private String fdNotifyData;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdEkpJavaNotifyMappForm> getFormClass() {
        return ThirdEkpJavaNotifyMappForm.class;
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
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
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
     * 待办ID
     */
    public String getFdNotifyId() {
        return this.fdNotifyId;
    }

    /**
     * 待办ID
     */
    public void setFdNotifyId(String fdNotifyId) {
        this.fdNotifyId = fdNotifyId;
    }

    /**
     * 待办相关信息
     */
    public String getFdNotifyData() {
        return (String) readLazyField("fdNotifyData", this.fdNotifyData);
    }

    /**
     * 待办相关信息
     */
    public void setFdNotifyData(String fdNotifyData) {
        this.fdNotifyData = (String) writeLazyField("fdNotifyData", this.fdNotifyData, fdNotifyData);
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
