package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.third.ding.model.ThirdDingDtaskXform;

/**
  * 钉钉流程任务
  */
public class ThirdDingDtaskXformForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdDingUserId;

    private String fdTaskId;

    private String fdEkpTaskId;

    private String fdUrl;

    private String fdStatus;

    private String fdDesc;

    private String docCreateTime;

    private String fdInstanceId;

    private String fdInstanceName;

    private String fdEkpUserId;

    private String fdEkpUserName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdDingUserId = null;
        fdTaskId = null;
        fdEkpTaskId = null;
        fdUrl = null;
        fdStatus = null;
        fdDesc = null;
        docCreateTime = null;
        fdInstanceId = null;
        fdInstanceName = null;
        fdEkpUserId = null;
        fdEkpUserName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingDtaskXform> getModelClass() {
        return ThirdDingDtaskXform.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("fdInstanceId", new FormConvertor_IDToModel("fdInstance", ThirdDingDinstanceXform.class));
            toModelPropertyMap.put("fdEkpUserId", new FormConvertor_IDToModel("fdEkpUser", SysOrgElement.class));
        }
        return toModelPropertyMap;
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
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 流程实例
     */
    public String getFdInstanceId() {
        return this.fdInstanceId;
    }

    /**
     * 流程实例
     */
    public void setFdInstanceId(String fdInstanceId) {
        this.fdInstanceId = fdInstanceId;
    }

    /**
     * 流程实例
     */
    public String getFdInstanceName() {
        return this.fdInstanceName;
    }

    /**
     * 流程实例
     */
    public void setFdInstanceName(String fdInstanceName) {
        this.fdInstanceName = fdInstanceName;
    }

    /**
     * ekp人员
     */
    public String getFdEkpUserId() {
        return this.fdEkpUserId;
    }

    /**
     * ekp人员
     */
    public void setFdEkpUserId(String fdEkpUserId) {
        this.fdEkpUserId = fdEkpUserId;
    }

    /**
     * ekp人员
     */
    public String getFdEkpUserName() {
        return this.fdEkpUserName;
    }

    /**
     * ekp人员
     */
    public void setFdEkpUserName(String fdEkpUserName) {
        this.fdEkpUserName = fdEkpUserName;
    }
}
