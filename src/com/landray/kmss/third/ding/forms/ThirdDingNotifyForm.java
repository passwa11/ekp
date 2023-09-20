package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingNotify;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 钉钉待办表
  */
public class ThirdDingNotifyForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdEkpUserId;

    private String fdModelId;

    private String fdModelName;

    private String fdParam1;

    private String fdParam2;

    private String fdRecordId;

    private String fdDingUserId;
    
    private String docCreateTime;
    
    private String fdEKPDel;

    public String getFdEKPDel() {
		return fdEKPDel;
	}

	public void setFdEKPDel(String fdEKPDel) {
		this.fdEKPDel = fdEKPDel;
	}

    public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdEkpUserId = null;
        fdModelId = null;
        fdModelName = null;
        fdParam1 = null;
        fdParam2 = null;
        fdRecordId = null;
        fdDingUserId = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingNotify> getModelClass() {
        return ThirdDingNotify.class;
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
     * EKP通知人
     */
    public String getFdEkpUserId() {
        return this.fdEkpUserId;
    }

    /**
     * EKP通知人
     */
    public void setFdEkpUserId(String fdEkpUserId) {
        this.fdEkpUserId = fdEkpUserId;
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
     * 参数一
     */
    public String getFdParam1() {
        return this.fdParam1;
    }

    /**
     * 参数一
     */
    public void setFdParam1(String fdParam1) {
        this.fdParam1 = fdParam1;
    }

    /**
     * 参数二
     */
    public String getFdParam2() {
        return this.fdParam2;
    }

    /**
     * 参数二
     */
    public void setFdParam2(String fdParam2) {
        this.fdParam2 = fdParam2;
    }

    /**
     * 钉钉业务ID
     */
    public String getFdRecordId() {
        return this.fdRecordId;
    }

    /**
     * 钉钉业务ID
     */
    public void setFdRecordId(String fdRecordId) {
        this.fdRecordId = fdRecordId;
    }

    /**
     * 钉钉通知人
     */
    public String getFdDingUserId() {
        return this.fdDingUserId;
    }

    /**
     * 钉钉通知人
     */
    public void setFdDingUserId(String fdDingUserId) {
        this.fdDingUserId = fdDingUserId;
    }
}
