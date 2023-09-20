package com.landray.kmss.fssc.budgeting.model;

import java.util.Date;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingApprovalLogForm;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 预算审批操作日志
  */
public class FsscBudgetingApprovalLog extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdMainId;

    private String fdDetailId;

    private String fdApprovalType;

    private Date fdApprovalTime;

    private String docSubject;

    private SysOrgPerson fdOperator;

    @Override
    public Class<FsscBudgetingApprovalLogForm> getFormClass() {
        return FsscBudgetingApprovalLogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdApprovalTime", new ModelConvertor_Common("fdApprovalTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("fdOperator.fdName", "fdOperatorName");
            toFormPropertyMap.put("fdOperator.fdId", "fdOperatorId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 预算编制主表ID
     */
    public String getFdMainId() {
        return this.fdMainId;
    }

    /**
     * 预算编制主表ID
     */
    public void setFdMainId(String fdMainId) {
        this.fdMainId = fdMainId;
    }

    /**
     * 预算编制明细ID
     */
    public String getFdDetailId() {
        return this.fdDetailId;
    }

    /**
     * 预算编制明细ID
     */
    public void setFdDetailId(String fdDetailId) {
        this.fdDetailId = fdDetailId;
    }

    /**
     * 审批方式
     */
    public String getFdApprovalType() {
        return this.fdApprovalType;
    }

    /**
     * 审批方式
     */
    public void setFdApprovalType(String fdApprovalType) {
        this.fdApprovalType = fdApprovalType;
    }

    /**
     * 审批时间
     */
    public Date getFdApprovalTime() {
        return this.fdApprovalTime;
    }

    /**
     * 审批时间
     */
    public void setFdApprovalTime(Date fdApprovalTime) {
        this.fdApprovalTime = fdApprovalTime;
    }

    /**
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 审批人
     */
    public SysOrgPerson getFdOperator() {
        return this.fdOperator;
    }

    /**
     * 审批人
     */
    public void setFdOperator(SysOrgPerson fdOperator) {
        this.fdOperator = fdOperator;
    }
}
