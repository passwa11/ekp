package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyRetireForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 员工退休
  */
public class HrRatifyRetire extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdRetireDate;

    private Date fdRetireEnterDate;

    private Integer fdRetireAge;

    private String fdRetireLeaderView;

    private String fdRetireHrView;

    private String fdRetireRemark;

    private SysOrgPerson fdRetireStaff;

    private SysOrgElement fdRetireDept;

	@Override
    public Class getFormClass() {
        return HrRatifyRetireForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdRetireDate", new ModelConvertor_Common("fdRetireDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdRetireEnterDate", new ModelConvertor_Common("fdRetireEnterDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdRetireStaff.fdName", "fdRetireStaffName");
            toFormPropertyMap.put("fdRetireStaff.fdId", "fdRetireStaffId");
            toFormPropertyMap.put("fdRetireDept.deptLevelNames", "fdRetireDeptName");
            toFormPropertyMap.put("fdRetireDept.fdId", "fdRetireDeptId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 退休日期
     */
    public Date getFdRetireDate() {
        return this.fdRetireDate;
    }

    /**
     * 退休日期
     */
    public void setFdRetireDate(Date fdRetireDate) {
        this.fdRetireDate = fdRetireDate;
    }

    /**
     * 入职日期
     */
    public Date getFdRetireEnterDate() {
        return this.fdRetireEnterDate;
    }

    /**
     * 入职日期
     */
    public void setFdRetireEnterDate(Date fdRetireEnterDate) {
        this.fdRetireEnterDate = fdRetireEnterDate;
    }

    /**
     * 退休年龄
     */
    public Integer getFdRetireAge() {
        return this.fdRetireAge;
    }

    /**
     * 退休年龄
     */
    public void setFdRetireAge(Integer fdRetireAge) {
        this.fdRetireAge = fdRetireAge;
    }

    /**
     * 部门领导意见
     */
    public String getFdRetireLeaderView() {
		return this.fdRetireLeaderView;
    }

    /**
     * 部门领导意见
     */
    public void setFdRetireLeaderView(String fdRetireLeaderView) {
		this.fdRetireLeaderView = fdRetireLeaderView;
    }

    /**
     * 人力资源部门意见
     */
    public String getFdRetireHrView() {
		return this.fdRetireHrView;
    }

    /**
     * 人力资源部门意见
     */
    public void setFdRetireHrView(String fdRetireHrView) {
		this.fdRetireHrView = fdRetireHrView;
    }

    /**
     * 备注
     */
    public String getFdRetireRemark() {
		return this.fdRetireRemark;
    }

    /**
     * 备注
     */
    public void setFdRetireRemark(String fdRetireRemark) {
		this.fdRetireRemark = fdRetireRemark;
    }

    /**
     * 退休人员
     */
    public SysOrgPerson getFdRetireStaff() {
        return this.fdRetireStaff;
    }

    /**
     * 退休人员
     */
    public void setFdRetireStaff(SysOrgPerson fdRetireStaff) {
        this.fdRetireStaff = fdRetireStaff;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getFdRetireDept() {
        return this.fdRetireDept;
    }

    /**
     * 所属部门
     */
    public void setFdRetireDept(SysOrgElement fdRetireDept) {
        this.fdRetireDept = fdRetireDept;
    }

}
