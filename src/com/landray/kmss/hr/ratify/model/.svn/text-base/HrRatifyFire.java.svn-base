package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyFireForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 员工解聘
  */
public class HrRatifyFire extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdFireDate;

    private Date fdFireEnterDate;

    private String fdFireReason;

    private String fdFireLeaderView;

    private String fdFireHrView;

    private String fdFireRemark;

    private SysOrgPerson fdFireStaff;

    private SysOrgElement fdFireDept;

	@Override
    public Class getFormClass() {
        return HrRatifyFireForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdFireDate", new ModelConvertor_Common("fdFireDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdFireEnterDate", new ModelConvertor_Common("fdFireEnterDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdFireStaff.fdName", "fdFireStaffName");
            toFormPropertyMap.put("fdFireStaff.fdId", "fdFireStaffId");
            toFormPropertyMap.put("fdFireDept.deptLevelNames", "fdFireDeptName");
            toFormPropertyMap.put("fdFireDept.fdId", "fdFireDeptId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 解聘日期
     */
    public Date getFdFireDate() {
        return this.fdFireDate;
    }

    /**
     * 解聘日期
     */
    public void setFdFireDate(Date fdFireDate) {
        this.fdFireDate = fdFireDate;
    }

    /**
     * 入职日期
     */
    public Date getFdFireEnterDate() {
        return this.fdFireEnterDate;
    }

    /**
     * 入职日期
     */
    public void setFdFireEnterDate(Date fdFireEnterDate) {
        this.fdFireEnterDate = fdFireEnterDate;
    }

    /**
     * 解聘原因
     */
    public String getFdFireReason() {
		return this.fdFireReason;
    }

    /**
     * 解聘原因
     */
    public void setFdFireReason(String fdFireReason) {
		this.fdFireReason =  fdFireReason;
    }

    /**
     * 部门领导意见
     */
    public String getFdFireLeaderView() {
		return this.fdFireLeaderView;
    }

    /**
     * 部门领导意见
     */
    public void setFdFireLeaderView(String fdFireLeaderView) {
		this.fdFireLeaderView =  fdFireLeaderView;
    }

    /**
     * 人力资源部门意见
     */
    public String getFdFireHrView() {
		return this.fdFireHrView;
    }

    /**
     * 人力资源部门意见
     */
    public void setFdFireHrView(String fdFireHrView) {
		this.fdFireHrView = (String) writeLazyField("fdFireHrView",
				this.fdFireHrView, fdFireHrView);
    }

    /**
     * 备注
     */
    public String getFdFireRemark() {
		return this.fdFireRemark;
    }

    /**
     * 备注
     */
    public void setFdFireRemark(String fdFireRemark) {
		this.fdFireRemark = fdFireRemark;
    }

    /**
     * 解聘人员
     */
    public SysOrgPerson getFdFireStaff() {
        return this.fdFireStaff;
    }

    /**
     * 解聘人员
     */
    public void setFdFireStaff(SysOrgPerson fdFireStaff) {
        this.fdFireStaff = fdFireStaff;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getFdFireDept() {
        return this.fdFireDept;
    }

    /**
     * 所属部门
     */
    public void setFdFireDept(SysOrgElement fdFireDept) {
        this.fdFireDept = fdFireDept;
    }

}
