package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifySalary;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 员工调薪
  */
public class HrRatifySalaryForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSalaryDate;

    private String fdSalaryBefore;

    private String fdSalaryAfter;

    private String fdSalaryDiff;

    private String fdSalaryReason;

    private String fdSalaryLeaderView;

    private String fdSalaryHrView;

    private String fdSalaryRemark;

    private String fdSalaryStaffId;

    private String fdSalaryStaffName;

    private String fdSalaryDeptId;

    private String fdSalaryDeptName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdSalaryDate = null;
        fdSalaryBefore = null;
        fdSalaryAfter = null;
        fdSalaryDiff = null;
        fdSalaryReason = null;
        fdSalaryLeaderView = null;
        fdSalaryHrView = null;
        fdSalaryRemark = null;
        fdSalaryStaffId = null;
        fdSalaryStaffName = null;
        fdSalaryDeptId = null;
        fdSalaryDeptName = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifySalary.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdSalaryDate", new FormConvertor_Common("fdSalaryDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdSalaryStaffId", new FormConvertor_IDToModel("fdSalaryStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdSalaryDeptId", new FormConvertor_IDToModel("fdSalaryDept", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 调薪日期
     */
    public String getFdSalaryDate() {
        return this.fdSalaryDate;
    }

    /**
     * 调薪日期
     */
    public void setFdSalaryDate(String fdSalaryDate) {
        this.fdSalaryDate = fdSalaryDate;
    }

    /**
     * 调整前薪酬
     */
    public String getFdSalaryBefore() {
		return HrRatifyUtil.doubleTrans(this.fdSalaryBefore);
    }

    /**
     * 调整前薪酬
     */
    public void setFdSalaryBefore(String fdSalaryBefore) {
        this.fdSalaryBefore = fdSalaryBefore;
    }

    /**
     * 调整后薪酬
     */
    public String getFdSalaryAfter() {
		return HrRatifyUtil.doubleTrans(this.fdSalaryAfter);
    }

    /**
     * 调整后薪酬
     */
    public void setFdSalaryAfter(String fdSalaryAfter) {
        this.fdSalaryAfter = fdSalaryAfter;
    }

    /**
     * 调薪金额
     */
    public String getFdSalaryDiff() {
		return HrRatifyUtil.doubleTrans(this.fdSalaryDiff);
    }

    /**
     * 调薪金额
     */
    public void setFdSalaryDiff(String fdSalaryDiff) {
        this.fdSalaryDiff = fdSalaryDiff;
    }

    /**
     * 调薪原因
     */
    public String getFdSalaryReason() {
        return this.fdSalaryReason;
    }

    /**
     * 调薪原因
     */
    public void setFdSalaryReason(String fdSalaryReason) {
        this.fdSalaryReason = fdSalaryReason;
    }

    /**
     * 部门领导意见
     */
    public String getFdSalaryLeaderView() {
        return this.fdSalaryLeaderView;
    }

    /**
     * 部门领导意见
     */
    public void setFdSalaryLeaderView(String fdSalaryLeaderView) {
        this.fdSalaryLeaderView = fdSalaryLeaderView;
    }

    /**
     * 人力资源部门意见
     */
    public String getFdSalaryHrView() {
        return this.fdSalaryHrView;
    }

    /**
     * 人力资源部门意见
     */
    public void setFdSalaryHrView(String fdSalaryHrView) {
        this.fdSalaryHrView = fdSalaryHrView;
    }

    /**
     * 备注
     */
    public String getFdSalaryRemark() {
        return this.fdSalaryRemark;
    }

    /**
     * 备注
     */
    public void setFdSalaryRemark(String fdSalaryRemark) {
        this.fdSalaryRemark = fdSalaryRemark;
    }

    /**
     * 调薪人员
     */
    public String getFdSalaryStaffId() {
        return this.fdSalaryStaffId;
    }

    /**
     * 调薪人员
     */
    public void setFdSalaryStaffId(String fdSalaryStaffId) {
        this.fdSalaryStaffId = fdSalaryStaffId;
    }

    /**
     * 调薪人员
     */
    public String getFdSalaryStaffName() {
        return this.fdSalaryStaffName;
    }

    /**
     * 调薪人员
     */
    public void setFdSalaryStaffName(String fdSalaryStaffName) {
        this.fdSalaryStaffName = fdSalaryStaffName;
    }

    /**
     * 所属部门
     */
    public String getFdSalaryDeptId() {
        return this.fdSalaryDeptId;
    }

    /**
     * 所属部门
     */
    public void setFdSalaryDeptId(String fdSalaryDeptId) {
        this.fdSalaryDeptId = fdSalaryDeptId;
    }

    /**
     * 所属部门
     */
    public String getFdSalaryDeptName() {
        return this.fdSalaryDeptName;
    }

    /**
     * 所属部门
     */
    public void setFdSalaryDeptName(String fdSalaryDeptName) {
        this.fdSalaryDeptName = fdSalaryDeptName;
    }

}
