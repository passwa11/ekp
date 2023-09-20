package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyRetire;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 员工退休
  */
public class HrRatifyRetireForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdRetireDate;

    private String fdRetireEnterDate;

    private String fdRetireAge;

    private String fdRetireLeaderView;

    private String fdRetireHrView;

    private String fdRetireRemark;

    private String fdRetireStaffId;

    private String fdRetireStaffName;

    private String fdRetireDeptId;

    private String fdRetireDeptName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdRetireDate = null;
        fdRetireEnterDate = null;
        fdRetireAge = null;
        fdRetireLeaderView = null;
        fdRetireHrView = null;
        fdRetireRemark = null;
        fdRetireStaffId = null;
        fdRetireStaffName = null;
        fdRetireDeptId = null;
        fdRetireDeptName = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyRetire.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdRetireDate", new FormConvertor_Common("fdRetireDate").setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdRetireEnterDate",
					new FormConvertor_Common("fdRetireEnterDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdRetireStaffId", new FormConvertor_IDToModel("fdRetireStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdRetireDeptId", new FormConvertor_IDToModel("fdRetireDept", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 退休日期
     */
    public String getFdRetireDate() {
        return this.fdRetireDate;
    }

    /**
     * 退休日期
     */
    public void setFdRetireDate(String fdRetireDate) {
        this.fdRetireDate = fdRetireDate;
    }

    /**
     * 入职日期
     */
    public String getFdRetireEnterDate() {
        return this.fdRetireEnterDate;
    }

    /**
     * 入职日期
     */
    public void setFdRetireEnterDate(String fdRetireEnterDate) {
        this.fdRetireEnterDate = fdRetireEnterDate;
    }

    /**
     * 退休年龄
     */
    public String getFdRetireAge() {
        return this.fdRetireAge;
    }

    /**
     * 退休年龄
     */
    public void setFdRetireAge(String fdRetireAge) {
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
    public String getFdRetireStaffId() {
        return this.fdRetireStaffId;
    }

    /**
     * 退休人员
     */
    public void setFdRetireStaffId(String fdRetireStaffId) {
        this.fdRetireStaffId = fdRetireStaffId;
    }

    /**
     * 退休人员
     */
    public String getFdRetireStaffName() {
        return this.fdRetireStaffName;
    }

    /**
     * 退休人员
     */
    public void setFdRetireStaffName(String fdRetireStaffName) {
        this.fdRetireStaffName = fdRetireStaffName;
    }

    /**
     * 所属部门
     */
    public String getFdRetireDeptId() {
        return this.fdRetireDeptId;
    }

    /**
     * 所属部门
     */
    public void setFdRetireDeptId(String fdRetireDeptId) {
        this.fdRetireDeptId = fdRetireDeptId;
    }

    /**
     * 所属部门
     */
    public String getFdRetireDeptName() {
        return this.fdRetireDeptName;
    }

    /**
     * 所属部门
     */
    public void setFdRetireDeptName(String fdRetireDeptName) {
        this.fdRetireDeptName = fdRetireDeptName;
    }

}
