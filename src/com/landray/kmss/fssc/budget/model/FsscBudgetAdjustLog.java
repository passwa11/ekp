package com.landray.kmss.fssc.budget.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.budget.forms.FsscBudgetAdjustLogForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 预算调整记录
  */
public class FsscBudgetAdjustLog extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdDesc;

    private Date docCreateTime;

    private String fdBudgetId;

    private String fdModelName;

    private String fdModelId;

    private Double fdAmount;

    private SysOrgPerson docCreator;

    @Override
    public Class<FsscBudgetAdjustLogForm> getFormClass() {
        return FsscBudgetAdjustLogForm.class;
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
    public Double getFdAmount() {
        return this.fdAmount;
    }

    /**
     * 调整金额
     */
    public void setFdAmount(Double fdAmount) {
        this.fdAmount = fdAmount;
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
