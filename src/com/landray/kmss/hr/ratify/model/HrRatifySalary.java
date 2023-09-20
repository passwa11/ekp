package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifySalaryForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 员工调薪
  */
public class HrRatifySalary extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdSalaryDate;

    private Double fdSalaryBefore;

    private Double fdSalaryAfter;

    private Double fdSalaryDiff;

    private String fdSalaryReason;

    private String fdSalaryLeaderView;

    private String fdSalaryHrView;

    private String fdSalaryRemark;

    private SysOrgPerson fdSalaryStaff;

    private SysOrgElement fdSalaryDept;

	@Override
    public Class getFormClass() {
        return HrRatifySalaryForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdSalaryDate", new ModelConvertor_Common("fdSalaryDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdSalaryStaff.fdName", "fdSalaryStaffName");
            toFormPropertyMap.put("fdSalaryStaff.fdId", "fdSalaryStaffId");
            toFormPropertyMap.put("fdSalaryDept.deptLevelNames", "fdSalaryDeptName");
            toFormPropertyMap.put("fdSalaryDept.fdId", "fdSalaryDeptId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 调薪日期
     */
    public Date getFdSalaryDate() {
        return this.fdSalaryDate;
    }

    /**
     * 调薪日期
     */
    public void setFdSalaryDate(Date fdSalaryDate) {
        this.fdSalaryDate = fdSalaryDate;
    }

    /**
     * 调整前薪酬
     */
    public Double getFdSalaryBefore() {
        return this.fdSalaryBefore;
    }

    /**
     * 调整前薪酬
     */
    public void setFdSalaryBefore(Double fdSalaryBefore) {
        this.fdSalaryBefore = fdSalaryBefore;
    }

    /**
     * 调整后薪酬
     */
    public Double getFdSalaryAfter() {
        return this.fdSalaryAfter;
    }

    /**
     * 调整后薪酬
     */
    public void setFdSalaryAfter(Double fdSalaryAfter) {
        this.fdSalaryAfter = fdSalaryAfter;
    }

    /**
     * 调薪金额
     */
    public Double getFdSalaryDiff() {
        return this.fdSalaryDiff;
    }

    /**
     * 调薪金额
     */
    public void setFdSalaryDiff(Double fdSalaryDiff) {
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
    public SysOrgPerson getFdSalaryStaff() {
        return this.fdSalaryStaff;
    }

    /**
     * 调薪人员
     */
    public void setFdSalaryStaff(SysOrgPerson fdSalaryStaff) {
        this.fdSalaryStaff = fdSalaryStaff;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getFdSalaryDept() {
        return this.fdSalaryDept;
    }

    /**
     * 所属部门
     */
    public void setFdSalaryDept(SysOrgElement fdSalaryDept) {
        this.fdSalaryDept = fdSalaryDept;
    }

}
