package com.landray.kmss.third.feishu.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import java.util.Date;
import com.landray.kmss.third.feishu.forms.ThirdFeishuNotifyQueueErrForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;

/**
  * 待办推送失败队列
  */
public class ThirdFeishuNotifyQueueErr extends BaseModel implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdSubject;

    private String fdMethod;

    private String fdData;

    private String fdErrMsg;

    private Integer fdRepeatHandle;

    private Integer fdFlag;

    private String fdMd5;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdFeishuUserId;

    private Integer fdSendTarget;

    private String fdNotifyId;

    private SysOrgElement fdPerson;

    @Override
    public Class<ThirdFeishuNotifyQueueErrForm> getFormClass() {
        return ThirdFeishuNotifyQueueErrForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
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
     * 待办MD5
     */
    public String getFdMd5() {
        return this.fdMd5;
    }

    /**
     * 待办MD5
     */
    public void setFdMd5(String fdMd5) {
        this.fdMd5 = fdMd5;
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
     * 飞书用户ID
     */
    public String getFdFeishuUserId() {
        return this.fdFeishuUserId;
    }

    /**
     * 飞书用户ID
     */
    public void setFdFeishuUserId(String fdFeishuUserId) {
        this.fdFeishuUserId = fdFeishuUserId;
    }

    /**
     * 推送目标
     */
    public Integer getFdSendTarget() {
        return this.fdSendTarget;
    }

    /**
     * 推送目标
     */
    public void setFdSendTarget(Integer fdSendTarget) {
        this.fdSendTarget = fdSendTarget;
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
     * 所属用户
     */
    public SysOrgElement getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 所属用户
     */
    public void setFdPerson(SysOrgElement fdPerson) {
        this.fdPerson = fdPerson;
    }
}
