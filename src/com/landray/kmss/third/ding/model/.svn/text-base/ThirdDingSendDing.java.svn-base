package com.landray.kmss.third.ding.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.third.ding.forms.ThirdDingSendDingForm;
import com.landray.kmss.util.DateUtil;

/**
  * DING日志
  */
public class ThirdDingSendDing extends BaseModel
		implements InterceptFieldEnabled {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date docCreateTime;

    private String fdLink;

    private String fdModelName;

    private String fdModelId;

    private Boolean fdStatus;

    private String fdSubject;

    private String fdAgentid;

    private String fdRemark;

    private String fdResult;

    private Boolean fdIsall;

    private String fdTarget;

    @Override
    public Class<ThirdDingSendDingForm> getFormClass() {
        return ThirdDingSendDingForm.class;
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
     * 发送时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 发送时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * DING链接
     */
    public String getFdLink() {
        return this.fdLink;
    }

    /**
     * DING链接
     */
    public void setFdLink(String fdLink) {
        this.fdLink = fdLink;
    }

    /**
     * 模块
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 模块
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 模块ID
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 模块ID
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 状态
     */
    public Boolean getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(Boolean fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 主题
     */
    public String getFdSubject() {
        return this.fdSubject;
    }

    /**
     * 主题
     */
    public void setFdSubject(String fdSubject) {
        this.fdSubject = fdSubject;
    }

    /**
     * AgentId
     */
    public String getFdAgentid() {
        return this.fdAgentid;
    }

    /**
     * AgentId
     */
    public void setFdAgentid(String fdAgentid) {
        this.fdAgentid = fdAgentid;
    }

    /**
     * 备注
     */
    public String getFdRemark() {
        return this.fdRemark;
    }

    /**
     * 备注
     */
    public void setFdRemark(String fdRemark) {
        this.fdRemark = fdRemark;
    }

    /**
     * 返回结果
     */
    public String getFdResult() {
        return this.fdResult;
    }

    /**
     * 返回结果
     */
    public void setFdResult(String fdResult) {
        this.fdResult = fdResult;
    }

    /**
     * 是否全员发送
     */
    public Boolean getFdIsall() {
        return this.fdIsall;
    }

    /**
     * 是否全员发送
     */
    public void setFdIsall(Boolean fdIsall) {
        this.fdIsall = fdIsall;
    }

    /**
     * 发送对象
     */
    public String getFdTarget() {
		return (String) readLazyField("fdTarget", this.fdTarget);
    }

    /**
     * 发送对象
     */
    public void setFdTarget(String fdTarget) {
		this.fdTarget = (String) writeLazyField("fdTarget", this.fdTarget,
				fdTarget);
    }
}
