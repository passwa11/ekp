package com.landray.kmss.third.weixin.work.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.weixin.work.forms.ThirdWeixinNotifyQueErrForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.DateUtil;

/**
  * 待办推送失败队列
  */
public class ThirdWeixinNotifyQueErr extends BaseModel implements InterceptFieldEnabled, IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdSubject;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdMethod;

    private String fdErrMsg;

    private Integer fdRepeatHandle;

    private String fdFlag;

    private String fdNotifyId;

    private String fdData;

    private String fdApiType;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<ThirdWeixinNotifyQueErrForm> getFormClass() {
        return ThirdWeixinNotifyQueErrForm.class;
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
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 标题
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
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
    public String getFdMethod() {
        return this.fdMethod;
    }

    /**
     * 动作
     */
    public void setFdMethod(String fdMethod) {
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
    public String getFdFlag() {
        return this.fdFlag;
    }

    /**
     * 处理标识
     */
    public void setFdFlag(String fdFlag) {
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

    /**
     * 接口类型
     */
    public String getFdApiType() {
        return this.fdApiType;
    }

    /**
     * 接口类型
     */
    public void setFdApiType(String fdApiType) {
        this.fdApiType = fdApiType;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getFdCorpId() {
        return fdCorpId;
    }

    public void setFdCorpId(String fdCorpId) {
        this.fdCorpId = fdCorpId;
    }

    private String fdCorpId;
}
