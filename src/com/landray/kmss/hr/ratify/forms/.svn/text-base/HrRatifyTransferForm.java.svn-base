package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyTransfer;
import com.landray.kmss.hr.ratify.util.HrRatifyUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 员工调岗
  */
public class HrRatifyTransferForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdTransferDate;

    private String fdTransferReason;

    private String fdTransferLeaveLeaderView;

    private String fdTransferEnterLeaderView;

    private String fdTransferHrView;

    private String fdTransferRemark;

    private String fdTransferStaffId;

    private String fdTransferStaffName;

    private String fdTransferLeaveDeptId;

    private String fdTransferLeaveDeptName;

    private String fdTransferEnterDeptId;

    private String fdTransferEnterDeptName;

	private String fdTransferLeavePostIds;

	private String fdTransferLeavePostNames;

	private String fdTransferEnterPostIds;

	private String fdTransferEnterPostNames;

	private String fdTransferOldLevelId;

	private String fdTransferOldLevelName;

	private String fdTransferNewLevelId;

	private String fdTransferNewLevelName;

	private String fdTransferLeaveSalary;

	private String fdTransferEnterSalary;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdTransferDate = null;
        fdTransferReason = null;
        fdTransferLeaveLeaderView = null;
        fdTransferEnterLeaderView = null;
        fdTransferHrView = null;
        fdTransferRemark = null;
        fdTransferStaffId = null;
        fdTransferStaffName = null;
        fdTransferLeaveDeptId = null;
        fdTransferLeaveDeptName = null;
        fdTransferEnterDeptId = null;
        fdTransferEnterDeptName = null;
		fdTransferLeavePostIds = null;
		fdTransferLeavePostNames = null;
		fdTransferEnterPostIds = null;
		fdTransferEnterPostNames = null;
		fdTransferOldLevelId = null;
		fdTransferOldLevelName = null;
		fdTransferNewLevelId = null;
		fdTransferNewLevelName = null;
		fdTransferEnterSalary = null;
		fdTransferEnterSalary = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyTransfer.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdTransferDate", new FormConvertor_Common("fdTransferDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdTransferStaffId", new FormConvertor_IDToModel("fdTransferStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdTransferLeaveDeptId", new FormConvertor_IDToModel("fdTransferLeaveDept", SysOrgElement.class));
            toModelPropertyMap.put("fdTransferEnterDeptId", new FormConvertor_IDToModel("fdTransferEnterDept", SysOrgElement.class));
			toModelPropertyMap.put("fdTransferLeavePostIds",
					new FormConvertor_IDsToModelList("fdTransferLeavePosts",
							SysOrgPost.class));
			toModelPropertyMap.put("fdTransferEnterPostIds",
					new FormConvertor_IDsToModelList("fdTransferEnterPosts",
							SysOrgPost.class));
			toModelPropertyMap.put("fdTransferOldLevelId",
					new FormConvertor_IDToModel("fdTransferOldLevel",
							SysOrganizationStaffingLevel.class));
			toModelPropertyMap.put("fdTransferNewLevelId",
					new FormConvertor_IDToModel("fdTransferNewLevel",
							SysOrganizationStaffingLevel.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 调岗日期
     */
    public String getFdTransferDate() {
        return this.fdTransferDate;
    }

    /**
     * 调岗日期
     */
    public void setFdTransferDate(String fdTransferDate) {
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
    public String getFdTransferStaffId() {
        return this.fdTransferStaffId;
    }

    /**
     * 调岗人员
     */
    public void setFdTransferStaffId(String fdTransferStaffId) {
        this.fdTransferStaffId = fdTransferStaffId;
    }

    /**
     * 调岗人员
     */
    public String getFdTransferStaffName() {
        return this.fdTransferStaffName;
    }

    /**
     * 调岗人员
     */
    public void setFdTransferStaffName(String fdTransferStaffName) {
        this.fdTransferStaffName = fdTransferStaffName;
    }

    /**
     * 调出部门
     */
    public String getFdTransferLeaveDeptId() {
        return this.fdTransferLeaveDeptId;
    }

    /**
     * 调出部门
     */
    public void setFdTransferLeaveDeptId(String fdTransferLeaveDeptId) {
        this.fdTransferLeaveDeptId = fdTransferLeaveDeptId;
    }

    /**
     * 调出部门
     */
    public String getFdTransferLeaveDeptName() {
        return this.fdTransferLeaveDeptName;
    }

    /**
     * 调出部门
     */
    public void setFdTransferLeaveDeptName(String fdTransferLeaveDeptName) {
        this.fdTransferLeaveDeptName = fdTransferLeaveDeptName;
    }

    /**
     * 调入部门
     */
    public String getFdTransferEnterDeptId() {
        return this.fdTransferEnterDeptId;
    }

    /**
     * 调入部门
     */
    public void setFdTransferEnterDeptId(String fdTransferEnterDeptId) {
        this.fdTransferEnterDeptId = fdTransferEnterDeptId;
    }

    /**
     * 调入部门
     */
    public String getFdTransferEnterDeptName() {
        return this.fdTransferEnterDeptName;
    }

    /**
     * 调入部门
     */
    public void setFdTransferEnterDeptName(String fdTransferEnterDeptName) {
        this.fdTransferEnterDeptName = fdTransferEnterDeptName;
    }

    /**
     * 调出岗位
     */
	public String getFdTransferLeavePostIds() {
		return this.fdTransferLeavePostIds;
    }

    /**
     * 调出岗位
     */
	public void setFdTransferLeavePostIds(String fdTransferLeavePostIds) {
		this.fdTransferLeavePostIds = fdTransferLeavePostIds;
    }

    /**
     * 调出岗位
     */
	public String getFdTransferLeavePostNames() {
		return this.fdTransferLeavePostNames;
    }

    /**
     * 调出岗位
     */
	public void setFdTransferLeavePostNames(String fdTransferLeavePostNames) {
		this.fdTransferLeavePostNames = fdTransferLeavePostNames;
    }

    /**
     * 调入岗位
     */
	public String getFdTransferEnterPostIds() {
		return this.fdTransferEnterPostIds;
    }

    /**
     * 调入岗位
     */
	public void setFdTransferEnterPostIds(String fdTransferEnterPostIds) {
		this.fdTransferEnterPostIds = fdTransferEnterPostIds;
    }

    /**
     * 调入岗位
     */
	public String getFdTransferEnterPostNames() {
		return this.fdTransferEnterPostNames;
    }

    /**
     * 调入岗位
     */
	public void setFdTransferEnterPostNames(String fdTransferEnterPostNames) {
		this.fdTransferEnterPostNames = fdTransferEnterPostNames;
    }

	public String getFdTransferOldLevelId() {
		return fdTransferOldLevelId;
	}

	public void setFdTransferOldLevelId(String fdTransferOldLevelId) {
		this.fdTransferOldLevelId = fdTransferOldLevelId;
	}

	public String getFdTransferOldLevelName() {
		return fdTransferOldLevelName;
	}

	public void setFdTransferOldLevelName(String fdTransferOldLevelName) {
		this.fdTransferOldLevelName = fdTransferOldLevelName;
	}

	public String getFdTransferNewLevelId() {
		return fdTransferNewLevelId;
	}

	public void setFdTransferNewLevelId(String fdTransferNewLevelId) {
		this.fdTransferNewLevelId = fdTransferNewLevelId;
	}

	public String getFdTransferNewLevelName() {
		return fdTransferNewLevelName;
	}

	public void setFdTransferNewLevelName(String fdTransferNewLevelName) {
		this.fdTransferNewLevelName = fdTransferNewLevelName;
	}

	/**
	 * 调动前薪酬
	 */
	public String getFdTransferLeaveSalary() {
		return HrRatifyUtil.doubleTrans(fdTransferLeaveSalary);
	}

	/**
	 * 调动前薪酬
	 */
	public void setFdTransferLeaveSalary(String fdTransferLeaveSalary) {
		this.fdTransferLeaveSalary = fdTransferLeaveSalary;
	}

	/**
	 * 调动后薪酬
	 */
	public String getFdTransferEnterSalary() {
		return HrRatifyUtil.doubleTrans(fdTransferEnterSalary);
	}

	/**
	 * 调动后薪酬
	 */
	public void setFdTransferEnterSalary(String fdTransferEnterSalary) {
		this.fdTransferEnterSalary = fdTransferEnterSalary;
	}

	/* 文件导入 */
	protected FormFile file = null;

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	/* 文件导入 */

}
