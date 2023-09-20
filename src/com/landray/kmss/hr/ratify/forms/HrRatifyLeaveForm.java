package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyLeave;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 员工离职
  */
public class HrRatifyLeaveForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdLeaveDate;

	private String fdLeaveRealDate;

    private String fdLeaveEnterDate;

    private String fdLeaveReason;

    private String fdLeaveLeaderView;

    private String fdLeaveHrView;

    private String fdLeaveRemark;

    private String fdLeaveStaffId;

    private String fdLeaveStaffName;

    private String fdLeaveDeptId;

    private String fdLeaveDeptName;

	private String fdLeaveStatus;

	private String fdNextCompany;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdLeaveDate = null;
		fdLeaveRealDate = null;
        fdLeaveEnterDate = null;
        fdLeaveReason = null;
        fdLeaveLeaderView = null;
        fdLeaveHrView = null;
        fdLeaveRemark = null;
        fdLeaveStaffId = null;
        fdLeaveStaffName = null;
        fdLeaveDeptId = null;
        fdLeaveDeptName = null;
		fdLeaveStatus = null;
		fdNextCompany = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyLeave.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdLeaveDate", new FormConvertor_Common("fdLeaveDate").setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdLeaveRealDate",
					new FormConvertor_Common("fdLeaveRealDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdLeaveEnterDate",
					new FormConvertor_Common("fdLeaveEnterDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdLeaveStaffId", new FormConvertor_IDToModel("fdLeaveStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdLeaveDeptId", new FormConvertor_IDToModel("fdLeaveDept", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
	 * 申请离职日期
	 */
    public String getFdLeaveDate() {
        return this.fdLeaveDate;
    }

    /**
	 * 申请离职日期
	 */
    public void setFdLeaveDate(String fdLeaveDate) {
        this.fdLeaveDate = fdLeaveDate;
    }

	/**
	 * 批准离职日期
	 */
	public String getFdLeaveRealDate() {
		return this.fdLeaveRealDate;
	}

	/**
	 * 批准离职日期
	 */
	public void setFdLeaveRealDate(String fdLeaveRealDate) {
		this.fdLeaveRealDate = fdLeaveRealDate;
	}

    /**
     * 入职日期
     */
    public String getFdLeaveEnterDate() {
        return this.fdLeaveEnterDate;
    }

    /**
     * 入职日期
     */
    public void setFdLeaveEnterDate(String fdLeaveEnterDate) {
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
        this.fdLeaveRemark = fdLeaveRemark;
    }

    /**
     * 离职人员
     */
    public String getFdLeaveStaffId() {
        return this.fdLeaveStaffId;
    }

    /**
     * 离职人员
     */
    public void setFdLeaveStaffId(String fdLeaveStaffId) {
        this.fdLeaveStaffId = fdLeaveStaffId;
    }

    /**
     * 离职人员
     */
    public String getFdLeaveStaffName() {
        return this.fdLeaveStaffName;
    }

    /**
     * 离职人员
     */
    public void setFdLeaveStaffName(String fdLeaveStaffName) {
        this.fdLeaveStaffName = fdLeaveStaffName;
    }

    /**
     * 所属部门
     */
    public String getFdLeaveDeptId() {
        return this.fdLeaveDeptId;
    }

    /**
     * 所属部门
     */
    public void setFdLeaveDeptId(String fdLeaveDeptId) {
        this.fdLeaveDeptId = fdLeaveDeptId;
    }

    /**
     * 所属部门
     */
    public String getFdLeaveDeptName() {
        return this.fdLeaveDeptName;
    }

    /**
     * 所属部门
     */
    public void setFdLeaveDeptName(String fdLeaveDeptName) {
        this.fdLeaveDeptName = fdLeaveDeptName;
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
