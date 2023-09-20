package com.landray.kmss.third.ding.forms;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉应用管理
  */
public class ThirdDingWorkForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String fdAgentid;

    private String fdSecret;

    private String fdToken;

    private String docCreateTime;

    private String fdModelName;

    private String fdUrlPrefix;

    private String fdAppKey;

    private String docCreatorId;

    private String docCreatorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        fdAgentid = null;
        fdSecret = null;
        fdToken = null;
        docCreateTime = null;
        fdModelName = null;
        fdUrlPrefix = null;
        fdAppKey = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingWork> getModelClass() {
        return ThirdDingWork.class;
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
     * 应用名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 应用名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
     * AppSecret
     */
    public String getFdSecret() {
        return this.fdSecret;
    }

    /**
     * AppSecret
     */
    public void setFdSecret(String fdSecret) {
        this.fdSecret = fdSecret;
    }

    /**
     * token
     */
    public String getFdToken() {
        return this.fdToken;
    }

    /**
     * token
     */
    public void setFdToken(String fdToken) {
        this.fdToken = fdToken;
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
     * 推送消息的模块
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 推送消息的模块
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 模块前缀列表
     */
    public String getFdUrlPrefix() {
        return this.fdUrlPrefix;
    }

    /**
     * 模块前缀列表
     */
    public void setFdUrlPrefix(String fdUrlPrefix) {
        this.fdUrlPrefix = fdUrlPrefix;
    }

    /**
     * AppKey
     */
    public String getFdAppKey() {
        return this.fdAppKey;
    }

    /**
     * AppKey
     */
    public void setFdAppKey(String fdAppKey) {
        this.fdAppKey = fdAppKey;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }
}
