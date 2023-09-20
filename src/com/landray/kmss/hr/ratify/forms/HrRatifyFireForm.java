package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyFire;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 员工解聘
  */
public class HrRatifyFireForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdFireDate;

    private String fdFireEnterDate;

    private String fdFireReason;

    private String fdFireLeaderView;

    private String fdFireHrView;

    private String fdFireRemark;

    private String fdFireStaffId;

    private String fdFireStaffName;

    private String fdFireDeptId;

    private String fdFireDeptName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdFireDate = null;
        fdFireEnterDate = null;
        fdFireReason = null;
        fdFireLeaderView = null;
        fdFireHrView = null;
        fdFireRemark = null;
        fdFireStaffId = null;
        fdFireStaffName = null;
        fdFireDeptId = null;
        fdFireDeptName = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyFire.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdFireDate", new FormConvertor_Common("fdFireDate").setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdFireEnterDate",
					new FormConvertor_Common("fdFireEnterDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdFireStaffId", new FormConvertor_IDToModel("fdFireStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdFireDeptId", new FormConvertor_IDToModel("fdFireDept", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 解聘日期
     */
    public String getFdFireDate() {
        return this.fdFireDate;
    }

    /**
     * 解聘日期
     */
    public void setFdFireDate(String fdFireDate) {
        this.fdFireDate = fdFireDate;
    }

    /**
     * 入职日期
     */
    public String getFdFireEnterDate() {
        return this.fdFireEnterDate;
    }

    /**
     * 入职日期
     */
    public void setFdFireEnterDate(String fdFireEnterDate) {
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
        this.fdFireReason = fdFireReason;
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
        this.fdFireLeaderView = fdFireLeaderView;
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
        this.fdFireHrView = fdFireHrView;
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
    public String getFdFireStaffId() {
        return this.fdFireStaffId;
    }

    /**
     * 解聘人员
     */
    public void setFdFireStaffId(String fdFireStaffId) {
        this.fdFireStaffId = fdFireStaffId;
    }

    /**
     * 解聘人员
     */
    public String getFdFireStaffName() {
        return this.fdFireStaffName;
    }

    /**
     * 解聘人员
     */
    public void setFdFireStaffName(String fdFireStaffName) {
        this.fdFireStaffName = fdFireStaffName;
    }

    /**
     * 所属部门
     */
    public String getFdFireDeptId() {
        return this.fdFireDeptId;
    }

    /**
     * 所属部门
     */
    public void setFdFireDeptId(String fdFireDeptId) {
        this.fdFireDeptId = fdFireDeptId;
    }

    /**
     * 所属部门
     */
    public String getFdFireDeptName() {
        return this.fdFireDeptName;
    }

    /**
     * 所属部门
     */
    public void setFdFireDeptName(String fdFireDeptName) {
        this.fdFireDeptName = fdFireDeptName;
    }

}
