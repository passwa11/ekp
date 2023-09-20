package com.landray.kmss.hr.organization.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.organization.forms.HrOrganizationConPostForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.util.DateUtil;

/**
  * 兼岗管理
  */
public class HrOrganizationConPost extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdStartTime;

    private Date fdEndTime;

    private String fdType;

	private HrStaffPersonInfo fdPerson;

	private HrOrganizationElement fdPost;

	private HrOrganizationElement fdDept;

	private SysOrganizationStaffingLevel fdStaffingLevel;

    @Override
    public Class<HrOrganizationConPostForm> getFormClass() {
        return HrOrganizationConPostForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdStartTime", new ModelConvertor_Common("fdStartTime").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdEndTime", new ModelConvertor_Common("fdEndTime").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
            toFormPropertyMap.put("fdPost.fdName", "fdPostName");
            toFormPropertyMap.put("fdPost.fdId", "fdPostId");
            toFormPropertyMap.put("fdDept.fdName", "fdDeptName");
            toFormPropertyMap.put("fdDept.fdId", "fdDeptId");
			toFormPropertyMap.put("fdStaffingLevel.fdName", "fdStaffingLevelName");
			toFormPropertyMap.put("fdStaffingLevel.fdId", "fdStaffingLevelId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 兼职开始时间
     */
    public Date getFdStartTime() {
        return this.fdStartTime;
    }

    /**
     * 兼职开始时间
     */
    public void setFdStartTime(Date fdStartTime) {
        this.fdStartTime = fdStartTime;
    }

    /**
     * 兼职结束时间
     */
    public Date getFdEndTime() {
        return this.fdEndTime;
    }

    /**
     * 兼职结束时间
     */
    public void setFdEndTime(Date fdEndTime) {
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
	public HrStaffPersonInfo getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 员工姓名
     */
	public void setFdPerson(HrStaffPersonInfo fdPerson) {
        this.fdPerson = fdPerson;
    }

    /**
     * 岗位
     */
	public HrOrganizationElement getFdPost() {
        return this.fdPost;
    }

    /**
     * 岗位
     */
	public void setFdPost(HrOrganizationElement fdPost) {
        this.fdPost = fdPost;
    }

    /**
     * 部门
     */
	public HrOrganizationElement getFdDept() {
        return this.fdDept;
    }

    /**
     * 部门
     */
	public void setFdDept(HrOrganizationElement fdDept) {
        this.fdDept = fdDept;
    }

	public SysOrganizationStaffingLevel getFdStaffingLevel() {
		return fdStaffingLevel;
	}

	public void setFdStaffingLevel(SysOrganizationStaffingLevel fdStaffingLevel) {
		this.fdStaffingLevel = fdStaffingLevel;
	}

}
