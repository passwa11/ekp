package com.landray.kmss.third.ding.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.ding.forms.ThirdDingDtaskForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 钉钉任务
  */
public class ThirdDingDtask extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdDingUserId;

    private String fdTaskId;

    private String fdEkpTaskId;

    private String fdUrl;

    private String fdStatus;

    private String fdDesc;

    private Date docCreateTime;

    private ThirdDingDinstance fdInstance;

    private SysOrgPerson fdEkpUser;

    @Override
    public Class<ThirdDingDtaskForm> getFormClass() {
        return ThirdDingDtaskForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdInstance.fdName", "fdInstanceName");
            toFormPropertyMap.put("fdInstance.fdId", "fdInstanceId");
            toFormPropertyMap.put("fdEkpUser.fdName", "fdEkpUserName");
            toFormPropertyMap.put("fdEkpUser.fdId", "fdEkpUserId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    /**
     * 钉钉接收人
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 钉钉接收人
     */
    public void setFdDingUserId(String fdDingUserId) {
        this.fdDingUserId = fdDingUserId;
    }

    /**
     * 任务Id
     */
    public String getFdTaskId() {
        return this.fdTaskId;
    }

    /**
     * 任务Id
     */
    public void setFdTaskId(String fdTaskId) {
        this.fdTaskId = fdTaskId;
    }

    /**
     * EKP任务Id
     */
    public String getFdEkpTaskId() {
        return this.fdEkpTaskId;
    }

    /**
     * EKP任务Id
     */
    public void setFdEkpTaskId(String fdEkpTaskId) {
        this.fdEkpTaskId = fdEkpTaskId;
    }

    /**
     * 任务地址
     */
    public String getFdUrl() {
        return this.fdUrl;
    }

    /**
     * 任务地址
     */
    public void setFdUrl(String fdUrl) {
        this.fdUrl = fdUrl;
    }

    /**
     * 发送状态
     */
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 发送状态
     */
    public void setFdStatus(String fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 发送详情
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 发送详情
     */
    public void setFdDesc(String fdDesc) {
        this.fdDesc = fdDesc;
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
     * 所属待办实例
     */
    public ThirdDingDinstance getFdInstance() {
        return this.fdInstance;
    }

    /**
     * 所属待办实例
     */
    public void setFdInstance(ThirdDingDinstance fdInstance) {
        this.fdInstance = fdInstance;
    }

    /**
     * EKP人员
     */
    public SysOrgPerson getFdEkpUser() {
        return this.fdEkpUser;
    }

    /**
     * EKP人员
     */
    public void setFdEkpUser(SysOrgPerson fdEkpUser) {
        this.fdEkpUser = fdEkpUser;
    }
}
