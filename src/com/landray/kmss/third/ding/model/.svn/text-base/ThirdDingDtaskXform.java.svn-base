package com.landray.kmss.third.ding.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.ding.forms.ThirdDingDtaskXformForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
  * 钉钉流程任务
  */
public class ThirdDingDtaskXform extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdDingUserId;

    private String fdTaskId;

    private String fdEkpTaskId;

    private String fdUrl;

    private String fdStatus;

    private String fdDesc;

    private Date docCreateTime;

    private ThirdDingDinstanceXform fdInstance;

    private SysOrgElement fdEkpUser;

    @Override
    public Class<ThirdDingDtaskXformForm> getFormClass() {
        return ThirdDingDtaskXformForm.class;
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
     * 任务ID
     */
    public String getFdTaskId() {
        return this.fdTaskId;
    }

    /**
     * 任务ID
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
     * 描述
     */
    public String getFdDesc() {
        return this.fdDesc;
    }

    /**
     * 描述
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
     * 流程实例
     */
    public ThirdDingDinstanceXform getFdInstance() {
        return this.fdInstance;
    }

    /**
     * 流程实例
     */
    public void setFdInstance(ThirdDingDinstanceXform fdInstance) {
        this.fdInstance = fdInstance;
    }

    /**
     * ekp人员
     */
    public SysOrgElement getFdEkpUser() {
        return this.fdEkpUser;
    }

    /**
     * ekp人员
     */
    public void setFdEkpUser(SysOrgElement fdEkpUser) {
        this.fdEkpUser = fdEkpUser;
    }
}
