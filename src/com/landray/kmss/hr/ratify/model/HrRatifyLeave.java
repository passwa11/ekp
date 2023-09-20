package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyLeaveForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 员工离职
  */
public class HrRatifyLeave extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdLeaveDate;

	private Date fdLeaveRealDate;

    private Date fdLeaveEnterDate;

    private String fdLeaveReason;

    private String fdLeaveLeaderView;

    private String fdLeaveHrView;

    private String fdLeaveRemark;

    private SysOrgPerson fdLeaveStaff;

    private SysOrgElement fdLeaveDept;

	private String fdLeaveStatus;

	private String fdNextCompany;

	@Override
    public Class getFormClass() {
        return HrRatifyLeaveForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdLeaveDate", new ModelConvertor_Common("fdLeaveDate").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdLeaveRealDate",
					new ModelConvertor_Common("fdLeaveRealDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdLeaveEnterDate", new ModelConvertor_Common("fdLeaveEnterDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdLeaveStaff.fdName", "fdLeaveStaffName");
            toFormPropertyMap.put("fdLeaveStaff.fdId", "fdLeaveStaffId");
            toFormPropertyMap.put("fdLeaveDept.deptLevelNames", "fdLeaveDeptName");
            toFormPropertyMap.put("fdLeaveDept.fdId", "fdLeaveDeptId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
	 * 申请离职日期
	 */
    public Date getFdLeaveDate() {
        return this.fdLeaveDate;
    }

    /**
	 * 申请离职日期
	 */
    public void setFdLeaveDate(Date fdLeaveDate) {
        this.fdLeaveDate = fdLeaveDate;
    }

	/**
	 * 批准离职日期
	 */
	public Date getFdLeaveRealDate() {
		return this.fdLeaveRealDate;
	}

	/**
	 * 批准离职日期
	 */
	public void setFdLeaveRealDate(Date fdLeaveRealDate) {
		this.fdLeaveRealDate = fdLeaveRealDate;
	}

    /**
     * 入职日期
     */
    public Date getFdLeaveEnterDate() {
        return this.fdLeaveEnterDate;
    }

    /**
     * 入职日期
     */
    public void setFdLeaveEnterDate(Date fdLeaveEnterDate) {
        this.fdLeaveEnterDate = fdLeaveEnterDate;
    }

    /**
     * 离职原因
     */
    public String getFdLeaveReason() {
		return this.fdLeaveReason;
    }

    /**
     * 离职原因
     */
    public void setFdLeaveReason(String fdLeaveReason) {
		this.fdLeaveReason = fdLeaveReason;
    }

    /**
     * 部门领导意见
     */
    public String getFdLeaveLeaderView() {
		return this.fdLeaveLeaderView;
    }

    /**
     * 部门领导意见
     */
    public void setFdLeaveLeaderView(String fdLeaveLeaderView) {
		this.fdLeaveLeaderView = fdLeaveLeaderView;
    }

    /**
     * 人力资源部门意见
     */
    public String getFdLeaveHrView() {
		return this.fdLeaveHrView;
    }

    /**
     * 人力资源部门意见
     */
    public void setFdLeaveHrView(String fdLeaveHrView) {
		this.fdLeaveHrView = fdLeaveHrView;
    }

    /**
     * 备注
     */
    public String getFdLeaveRemark() {
		return this.fdLeaveRemark;
    }

    /**
     * 备注
     */
    public void setFdLeaveRemark(String fdLeaveRemark) {
		this.fdLeaveRemark =  fdLeaveRemark;
    }

    /**
     * 离职人员
     */
    public SysOrgPerson getFdLeaveStaff() {
        return this.fdLeaveStaff;
    }

    /**
     * 离职人员
     */
    public void setFdLeaveStaff(SysOrgPerson fdLeaveStaff) {
        this.fdLeaveStaff = fdLeaveStaff;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getFdLeaveDept() {
        return this.fdLeaveDept;
    }

    /**
     * 所属部门
     */
    public void setFdLeaveDept(SysOrgElement fdLeaveDept) {
        this.fdLeaveDept = fdLeaveDept;
    }

	public String getFdLeaveStatus() {
		return fdLeaveStatus;
	}

	public void setFdLeaveStatus(String fdLeaveStatus) {
		this.fdLeaveStatus = fdLeaveStatus;
	}

	/**
	 * 离职去向
	 * 
	 * @return
	 */
	public String getFdNextCompany() {
		return fdNextCompany;
	}

	/**
	 * 离职去向
	 * 
	 * @return
	 */
	public void setFdNextCompany(String fdNextCompany) {
		this.fdNextCompany = fdNextCompany;
	}

}
