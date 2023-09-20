package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingFinstance;
import com.landray.kmss.web.action.ActionMapping;

/**
  * EKP流程集成实例表
  */
public class ThirdDingFinstanceForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdTemplateId;

    private String fdModelId;

    private String fdModelName;

    private String fdEkpStatus;

    private String fdStartFlow;

    private String fdProcessCode;

    private String fdInstanceId;

    private String fdDingStatus;
    
    private String fdCreateTime;

    public String getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdTemplateId = null;
        fdModelId = null;
        fdModelName = null;
        fdEkpStatus = null;
        fdStartFlow = null;
        fdProcessCode = null;
        fdInstanceId = null;
        fdDingStatus = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingFinstance> getModelClass() {
        return ThirdDingFinstance.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
    }

    /**
     * EKP流程模板Id
     */
    public String getFdTemplateId() {
        return this.fdTemplateId;
    }

    /**
     * EKP流程模板Id
     */
    public void setFdTemplateId(String fdTemplateId) {
        this.fdTemplateId = fdTemplateId;
    }

    /**
     * modelId
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * modelId
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * modelName
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * modelName
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * EKP流程状态
     */
    public String getFdEkpStatus() {
        return this.fdEkpStatus;
    }

    /**
     * EKP流程状态
     */
    public void setFdEkpStatus(String fdEkpStatus) {
        this.fdEkpStatus = fdEkpStatus;
    }

    /**
     * 流程启动方
     */
    public String getFdStartFlow() {
        return this.fdStartFlow;
    }

    /**
     * 流程启动方
     */
    public void setFdStartFlow(String fdStartFlow) {
        this.fdStartFlow = fdStartFlow;
    }

    /**
     * 钉钉流程模板Code
     */
    public String getFdProcessCode() {
        return this.fdProcessCode;
    }

    /**
     * 钉钉流程模板Code
     */
    public void setFdProcessCode(String fdProcessCode) {
        this.fdProcessCode = fdProcessCode;
    }

    /**
     * 钉钉流程实例ID
     */
    public String getFdInstanceId() {
        return this.fdInstanceId;
    }

    /**
     * 钉钉流程实例ID
     */
    public void setFdInstanceId(String fdInstanceId) {
        this.fdInstanceId = fdInstanceId;
    }

    /**
     * 钉钉流程状态
     */
    public String getFdDingStatus() {
        return this.fdDingStatus;
    }

    /**
     * 钉钉流程状态
     */
    public void setFdDingStatus(String fdDingStatus) {
        this.fdDingStatus = fdDingStatus;
    }
}
