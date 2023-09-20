package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyTransferForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 员工调岗
  */
public class HrRatifyTransfer extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdTransferDate;

    private String fdTransferReason;

    private String fdTransferLeaveLeaderView;

    private String fdTransferEnterLeaderView;

    private String fdTransferHrView;

    private String fdTransferRemark;

    private SysOrgPerson fdTransferStaff;

    private SysOrgElement fdTransferLeaveDept;

    private SysOrgElement fdTransferEnterDept;

	private SysOrganizationStaffingLevel fdTransferOldLevel;

	private SysOrganizationStaffingLevel fdTransferNewLevel;

	private Double fdTransferLeaveSalary;

	private Double fdTransferEnterSalary;

	@Override
    public Class getFormClass() {
        return HrRatifyTransferForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdTransferDate", new ModelConvertor_Common("fdTransferDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdTransferStaff.fdName", "fdTransferStaffName");
            toFormPropertyMap.put("fdTransferStaff.fdId", "fdTransferStaffId");
            toFormPropertyMap.put("fdTransferLeaveDept.deptLevelNames", "fdTransferLeaveDeptName");
            toFormPropertyMap.put("fdTransferLeaveDept.fdId", "fdTransferLeaveDeptId");
            toFormPropertyMap.put("fdTransferEnterDept.deptLevelNames", "fdTransferEnterDeptName");
            toFormPropertyMap.put("fdTransferEnterDept.fdId", "fdTransferEnterDeptId");
			toFormPropertyMap.put("fdTransferLeavePosts",
					new ModelConvertor_ModelListToString(
							"fdTransferLeavePostIds:fdTransferLeavePostNames",
							"fdId:fdName"));
			toFormPropertyMap.put("fdTransferEnterPosts",
					new ModelConvertor_ModelListToString(
							"fdTransferEnterPostIds:fdTransferEnterPostNames",
							"fdId:fdName"));
			toFormPropertyMap.put("fdTransferOldLevel.fdId",
					"fdTransferOldLevelId");
			toFormPropertyMap.put("fdTransferOldLevel.fdName",
					"fdTransferOldLevelName");
			toFormPropertyMap.put("fdTransferNewLevel.fdId",
					"fdTransferNewLevelId");
			toFormPropertyMap.put("fdTransferNewLevel.fdName",
					"fdTransferNewLevelName");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 调岗日期
     */
    public Date getFdTransferDate() {
        return this.fdTransferDate;
    }

    /**
     * 调岗日期
     */
    public void setFdTransferDate(Date fdTransferDate) {
        this.fdTransferDate = fdTransferDate;
    }

    /**
     * 调岗原因
     */
    public String getFdTransferReason() {
		return this.fdTransferReason;
    }

    /**
     * 调岗原因
     */
    public void setFdTransferReason(String fdTransferReason) {
		this.fdTransferReason = fdTransferReason;
    }

    /**
     * 调出部门领导意见
     */
    public String getFdTransferLeaveLeaderView() {
		return this.fdTransferLeaveLeaderView;
    }

    /**
     * 调出部门领导意见
     */
    public void setFdTransferLeaveLeaderView(String fdTransferLeaveLeaderView) {
		this.fdTransferLeaveLeaderView = fdTransferLeaveLeaderView;
    }

    /**
     * 调入部门领导意见
     */
    public String getFdTransferEnterLeaderView() {
		return this.fdTransferEnterLeaderView;
    }

    /**
     * 调入部门领导意见
     */
    public void setFdTransferEnterLeaderView(String fdTransferEnterLeaderView) {
		this.fdTransferEnterLeaderView = fdTransferEnterLeaderView;
    }

    /**
     * 人力资源部门意见
     */
    public String getFdTransferHrView() {
		return this.fdTransferHrView;
    }

    /**
     * 人力资源部门意见
     */
    public void setFdTransferHrView(String fdTransferHrView) {
		this.fdTransferHrView = fdTransferHrView;
    }

    /**
     * 备注
     */
    public String getFdTransferRemark() {
		return this.fdTransferRemark;
    }

    /**
     * 备注
     */
    public void setFdTransferRemark(String fdTransferRemark) {
		this.fdTransferRemark = fdTransferRemark;
    }

    /**
     * 调岗人员
     */
    public SysOrgPerson getFdTransferStaff() {
        return this.fdTransferStaff;
    }

    /**
     * 调岗人员
     */
    public void setFdTransferStaff(SysOrgPerson fdTransferStaff) {
        this.fdTransferStaff = fdTransferStaff;
    }

    /**
     * 调出部门
     */
    public SysOrgElement getFdTransferLeaveDept() {
        return this.fdTransferLeaveDept;
    }

    /**
     * 调出部门
     */
    public void setFdTransferLeaveDept(SysOrgElement fdTransferLeaveDept) {
        this.fdTransferLeaveDept = fdTransferLeaveDept;
    }

    /**
     * 调入部门
     */
    public SysOrgElement getFdTransferEnterDept() {
        return this.fdTransferEnterDept;
    }

    /**
     * 调入部门
     */
    public void setFdTransferEnterDept(SysOrgElement fdTransferEnterDept) {
        this.fdTransferEnterDept = fdTransferEnterDept;
    }

	/**
	 * 原职级
	 */
	public SysOrganizationStaffingLevel getFdTransferOldLevel() {
		return fdTransferOldLevel;
	}

	/**
	 * 原职级
	 */
	public void
			setFdTransferOldLevel(
					SysOrganizationStaffingLevel fdTransferOldLevel) {
		this.fdTransferOldLevel = fdTransferOldLevel;
	}

	/**
	 * 现职级
	 */
	public SysOrganizationStaffingLevel getFdTransferNewLevel() {
		return fdTransferNewLevel;
	}

	/**
	 * 现职级
	 */
	public void
			setFdTransferNewLevel(
					SysOrganizationStaffingLevel fdTransferNewLevel) {
		this.fdTransferNewLevel = fdTransferNewLevel;
	}

	/**
	 * 调动前薪酬
	 */
	public Double getFdTransferLeaveSalary() {
		return fdTransferLeaveSalary;
	}

	/**
	 * 调动前薪酬
	 */
	public void setFdTransferLeaveSalary(Double fdTransferLeaveSalary) {
		this.fdTransferLeaveSalary = fdTransferLeaveSalary;
	}

	/**
	 * 调动后薪酬
	 */
	public Double getFdTransferEnterSalary() {
		return fdTransferEnterSalary;
	}

	/**
	 * 调动后薪酬
	 */
	public void setFdTransferEnterSalary(Double fdTransferEnterSalary) {
		this.fdTransferEnterSalary = fdTransferEnterSalary;
	}
}
