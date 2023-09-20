package com.landray.kmss.hr.organization.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.hr.organization.model.HrOrganizationConPost;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 兼岗管理
  */
public class HrOrganizationConPostForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdStartTime;

    private String fdEndTime;

    private String fdType;

    private String fdPersonId;

    private String fdPersonName;

    private String fdPostId;

    private String fdPostName;

    private String fdDeptId;

    private String fdDeptName;

	private String fdStaffingLevelId;

	private String fdStaffingLevelName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdStartTime = null;
        fdEndTime = null;
        fdType = null;
        fdPersonId = null;
        fdPersonName = null;
        fdPostId = null;
        fdPostName = null;
        fdDeptId = null;
        fdDeptName = null;
		fdStaffingLevelId = null;
		fdStaffingLevelName = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<HrOrganizationConPost> getModelClass() {
        return HrOrganizationConPost.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdStartTime", new FormConvertor_Common("fdStartTime").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdEndTime", new FormConvertor_Common("fdEndTime").setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", HrStaffPersonInfo.class));
            toModelPropertyMap.put("fdPostId", new FormConvertor_IDToModel("fdPost", HrOrganizationPost.class));
            toModelPropertyMap.put("fdDeptId", new FormConvertor_IDToModel("fdDept", HrOrganizationDept.class));
			toModelPropertyMap.put("fdStaffingLevelId",
					new FormConvertor_IDToModel("fdStaffingLevel", SysOrganizationStaffingLevel.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 兼职开始时间
     */
    public String getFdStartTime() {
        return this.fdStartTime;
    }

    /**
     * 兼职开始时间
     */
    public void setFdStartTime(String fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 兼职结束时间
     */
    public String getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 兼职结束时间
     */
    public void setFdEndTime(String fdEndTime) {
        this.fdEndTime = fdEndTime;
    }

    /**
     * 任职类型
     */
    public String getFdType() {
        return this.fdType;
    }

    /**
     * 任职类型
     */
    public void setFdType(String fdType) {
        this.fdType = fdType;
    }

    /**
     * 员工姓名
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 员工姓名
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 员工姓名
     */
    public String getFdPersonName() {
        return this.fdPersonName;
    }

    /**
     * 员工姓名
     */
    public void setFdPersonName(String fdPersonName) {
        this.fdPersonName = fdPersonName;
    }

    /**
     * 岗位
     */
    public String getFdPostId() {
        return this.fdPostId;
    }

    /**
     * 岗位
     */
    public void setFdPostId(String fdPostId) {
        this.fdPostId = fdPostId;
    }

    /**
     * 岗位
     */
    public String getFdPostName() {
        return this.fdPostName;
    }

    /**
     * 岗位
     */
    public void setFdPostName(String fdPostName) {
        this.fdPostName = fdPostName;
    }

    /**
     * 部门
     */
    public String getFdDeptId() {
        return this.fdDeptId;
    }

    /**
     * 部门
     */
    public void setFdDeptId(String fdDeptId) {
        this.fdDeptId = fdDeptId;
    }

    /**
     * 部门
     */
    public String getFdDeptName() {
        return this.fdDeptName;
    }

    /**
     * 部门
     */
    public void setFdDeptName(String fdDeptName) {
        this.fdDeptName = fdDeptName;
    }

	public String getFdStaffingLevelId() {
		return fdStaffingLevelId;
	}

	public String getFdStaffingLevelName() {
		return fdStaffingLevelName;
	}

	public void setFdStaffingLevelId(String fdStaffingLevelId) {
		this.fdStaffingLevelId = fdStaffingLevelId;
	}

	public void setFdStaffingLevelName(String fdStaffingLevelName) {
		this.fdStaffingLevelName = fdStaffingLevelName;
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
