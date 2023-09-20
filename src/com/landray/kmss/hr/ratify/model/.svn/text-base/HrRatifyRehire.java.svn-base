package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyRehireForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 员工返聘
  */
public class HrRatifyRehire extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdRehireDate;

    private Date fdRehireEnterDate;

    private String fdRehireReason;

    private String fdRehireLeaderView;

    private String fdRehireHrView;

    private String fdRehireRemark;

    private SysOrgPerson fdRehireStaff;

    private SysOrgElement fdRehireDept;

	@Override
    public Class getFormClass() {
        return HrRatifyRehireForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdRehireDate", new ModelConvertor_Common("fdRehireDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdRehireEnterDate", new ModelConvertor_Common("fdRehireEnterDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdRehireStaff.fdName", "fdRehireStaffName");
            toFormPropertyMap.put("fdRehireStaff.fdId", "fdRehireStaffId");
            toFormPropertyMap.put("fdRehireDept.deptLevelNames", "fdRehireDeptName");
            toFormPropertyMap.put("fdRehireDept.fdId", "fdRehireDeptId");
			toFormPropertyMap.put("fdRehirePosts",
					new ModelConvertor_ModelListToString(
							"fdRehirePostIds:fdRehirePostNames",
							"fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 返聘日期
     */
    public Date getFdRehireDate() {
        return this.fdRehireDate;
    }

    /**
     * 返聘日期
     */
    public void setFdRehireDate(Date fdRehireDate) {
        this.fdRehireDate = fdRehireDate;
    }

    /**
     * 原入职日期
     */
    public Date getFdRehireEnterDate() {
        return this.fdRehireEnterDate;
    }

    /**
     * 原入职日期
     */
    public void setFdRehireEnterDate(Date fdRehireEnterDate) {
        this.fdRehireEnterDate = fdRehireEnterDate;
    }

    /**
     * 返聘原因
     */
    public String getFdRehireReason() {
		return this.fdRehireReason;
    }

    /**
     * 返聘原因
     */
    public void setFdRehireReason(String fdRehireReason) {
		this.fdRehireReason = fdRehireReason;
    }

    /**
     * 部门领导意见
     */
    public String getFdRehireLeaderView() {
		return this.fdRehireLeaderView;
    }

    /**
     * 部门领导意见
     */
    public void setFdRehireLeaderView(String fdRehireLeaderView) {
		this.fdRehireLeaderView = fdRehireLeaderView;
    }

    /**
     * 人力资源部门意见
     */
    public String getFdRehireHrView() {
		return this.fdRehireHrView;
    }

    /**
     * 人力资源部门意见
     */
    public void setFdRehireHrView(String fdRehireHrView) {
		this.fdRehireHrView = fdRehireHrView;
    }

    /**
     * 备注
     */
    public String getFdRehireRemark() {
		return this.fdRehireRemark;
    }

    /**
     * 备注
     */
    public void setFdRehireRemark(String fdRehireRemark) {
		this.fdRehireRemark = fdRehireRemark;
    }

    /**
     * 返聘员工
     */
    public SysOrgPerson getFdRehireStaff() {
        return this.fdRehireStaff;
    }

    /**
     * 返聘员工
     */
    public void setFdRehireStaff(SysOrgPerson fdRehireStaff) {
        this.fdRehireStaff = fdRehireStaff;
    }

    /**
     * 返聘部门
     */
    public SysOrgElement getFdRehireDept() {
        return this.fdRehireDept;
    }

    /**
     * 返聘部门
     */
    public void setFdRehireDept(SysOrgElement fdRehireDept) {
        this.fdRehireDept = fdRehireDept;
    }

}
