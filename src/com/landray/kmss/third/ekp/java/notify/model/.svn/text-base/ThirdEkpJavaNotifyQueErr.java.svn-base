package com.landray.kmss.third.ekp.java.notify.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.third.ekp.java.notify.forms.ThirdEkpJavaNotifyQueErrForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
  * 待办推送失败队列
  */
public class ThirdEkpJavaNotifyQueErr extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private Date docCreateTime;

    private Date docAlterTime;

	private Integer fdMethod;

    private String fdErrMsg;

    private Integer fdRepeatHandle;

    private Integer fdFlag;

    private String fdNotifyId;

    private String fdData;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdEkpJavaNotifyQueErrForm> getFormClass() {
        return ThirdEkpJavaNotifyQueErrForm.class;
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
     * 动作
     */
	public Integer getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 动作
     */
	public void setFdMethod(Integer fdMethod) {
        this.fdMethod = fdMethod;
    }

    /**
     * 异常信息
     */
    public String getFdErrMsg() {
        return (String) readLazyField("fdErrMsg", this.fdErrMsg);
    }

    /**
     * 异常信息
     */
    public void setFdErrMsg(String fdErrMsg) {
        this.fdErrMsg = (String) writeLazyField("fdErrMsg", this.fdErrMsg, fdErrMsg);
    }

    /**
     * 重复处理次数
     */
    public Integer getFdRepeatHandle() {
        return this.fdRepeatHandle;
    }

    /**
     * 重复处理次数
     */
    public void setFdRepeatHandle(Integer fdRepeatHandle) {
        this.fdRepeatHandle = fdRepeatHandle;
    }

    /**
     * 处理标识
     */
    public Integer getFdFlag() {
        return this.fdFlag;
    }

    /**
     * 处理标识
     */
    public void setFdFlag(Integer fdFlag) {
        this.fdFlag = fdFlag;
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
     * 消息内容
     */
    public String getFdData() {
        return (String) readLazyField("fdData", this.fdData);
    }

    /**
     * 消息内容
     */
    public void setFdData(String fdData) {
        this.fdData = (String) writeLazyField("fdData", this.fdData, fdData);
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
