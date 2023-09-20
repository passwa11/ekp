package com.landray.kmss.fssc.budgeting.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalLog;

/**
  * 预算审批操作日志
  */
public class FsscBudgetingApprovalLogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdMainId;

    private String fdDetailId;

    private String fdApprovalType;

    private String fdApprovalTime;

    private String docSubject;

    private String fdOperatorId;

    private String fdOperatorName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdMainId = null;
        fdDetailId = null;
        fdApprovalType = null;
        fdApprovalTime = null;
        docSubject = null;
        fdOperatorId = null;
        fdOperatorName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetingApprovalLog> getModelClass() {
        return FsscBudgetingApprovalLog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdApprovalTime", new FormConvertor_Common("fdApprovalTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("fdOperatorId", new FormConvertor_IDToModel("fdOperator", SysOrgPerson.class));
        }
        return toModelPropertyMap;
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
    public String getFdApprovalTime() {
        return this.fdApprovalTime;
    }

    /**
     * 审批时间
     */
    public void setFdApprovalTime(String fdApprovalTime) {
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
    public String getFdOperatorId() {
        return this.fdOperatorId;
    }

    /**
     * 审批人
     */
    public void setFdOperatorId(String fdOperatorId) {
        this.fdOperatorId = fdOperatorId;
    }

    /**
     * 审批人
     */
    public String getFdOperatorName() {
        return this.fdOperatorName;
    }

    /**
     * 审批人
     */
    public void setFdOperatorName(String fdOperatorName) {
        this.fdOperatorName = fdOperatorName;
    }
}
