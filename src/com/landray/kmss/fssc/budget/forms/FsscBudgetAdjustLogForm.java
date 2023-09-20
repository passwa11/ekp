package com.landray.kmss.fssc.budget.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustLog;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 预算调整记录
  */
public class FsscBudgetAdjustLogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdDesc;

    private String docCreateTime;

    private String fdBudgetId;

    private String fdModelName;

    private String fdModelId;

    private String fdAmount;

    private String docCreatorId;

    private String docCreatorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdDesc = null;
        docCreateTime = null;
        fdBudgetId = null;
        fdModelName = null;
        fdModelId = null;
        fdAmount = null;
        docCreatorId = null;
        docCreatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetAdjustLog> getModelClass() {
        return FsscBudgetAdjustLog.class;
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
     * 对应预算ID
     */
    public String getFdBudgetId() {
        return this.fdBudgetId;
    }

    /**
     * 对应预算ID
     */
    public void setFdBudgetId(String fdBudgetId) {
        this.fdBudgetId = fdBudgetId;
    }

    /**
     * 预算调整来源域
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 预算调整来源域
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 预算调整来源单据ID
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 预算调整来源单据ID
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 调整金额
     */
    public String getFdAmount() {
        return this.fdAmount;
    }

    /**
     * 调整金额
     */
    public void setFdAmount(String fdAmount) {
        this.fdAmount = fdAmount;
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
