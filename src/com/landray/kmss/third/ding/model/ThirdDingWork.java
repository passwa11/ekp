package com.landray.kmss.third.ding.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.third.ding.forms.ThirdDingWorkForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 钉钉应用管理
  */
public class ThirdDingWork extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private String fdAgentid;

    private String fdSecret;

    private String fdToken;

    private Date docCreateTime;

    private String fdModelName;

    private String fdUrlPrefix;

    private String fdAppKey;

    private SysOrgPerson docCreator;

    @Override
    public Class<ThirdDingWorkForm> getFormClass() {
        return ThirdDingWorkForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }
}
