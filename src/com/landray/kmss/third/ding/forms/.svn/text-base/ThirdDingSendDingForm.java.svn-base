package com.landray.kmss.third.ding.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingSendDing;
import com.landray.kmss.web.action.ActionMapping;

/**
  * DING日志
  */
public class ThirdDingSendDingForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdLink;

    private String fdModelName;

    private String fdModelId;

    private String fdStatus;

    private String fdSubject;

    private String fdAgentid;

    private String fdRemark;

    private String fdResult;

    private String fdIsall;

    private String fdTarget;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docCreateTime = null;
        fdLink = null;
        fdModelName = null;
        fdModelId = null;
        fdStatus = null;
        fdSubject = null;
        fdAgentid = null;
        fdRemark = null;
        fdResult = null;
        fdIsall = null;
        fdTarget = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingSendDing> getModelClass() {
        return ThirdDingSendDing.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
        }
        return toModelPropertyMap;
    }

    /**
     * 发送时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 发送时间
     */
    public void setDocCreateTime(String docCreateTime) {
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
    public String getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(String fdStatus) {
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
    public String getFdIsall() {
        return this.fdIsall;
    }

    /**
     * 是否全员发送
     */
    public void setFdIsall(String fdIsall) {
        this.fdIsall = fdIsall;
    }

    /**
     * 发送对象
     */
    public String getFdTarget() {
        return this.fdTarget;
    }

    /**
     * 发送对象
     */
    public void setFdTarget(String fdTarget) {
        this.fdTarget = fdTarget;
    }
}
