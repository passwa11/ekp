package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyRehire;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 员工返聘
  */
public class HrRatifyRehireForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdRehireDate;

    private String fdRehireEnterDate;

    private String fdRehireReason;

    private String fdRehireLeaderView;

    private String fdRehireHrView;

    private String fdRehireRemark;

    private String fdRehireStaffId;

    private String fdRehireStaffName;

    private String fdRehireDeptId;

    private String fdRehireDeptName;

	private String fdRehirePostIds;

	private String fdRehirePostNames;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdRehireDate = null;
        fdRehireEnterDate = null;
        fdRehireReason = null;
        fdRehireLeaderView = null;
        fdRehireHrView = null;
        fdRehireRemark = null;
        fdRehireStaffId = null;
        fdRehireStaffName = null;
        fdRehireDeptId = null;
        fdRehireDeptName = null;
		fdRehirePostIds = null;
		fdRehirePostNames = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyRehire.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdRehireDate", new FormConvertor_Common("fdRehireDate").setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdRehireEnterDate",
					new FormConvertor_Common("fdRehireEnterDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdRehireStaffId", new FormConvertor_IDToModel("fdRehireStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdRehireDeptId", new FormConvertor_IDToModel("fdRehireDept", SysOrgElement.class));
			toModelPropertyMap.put("fdRehirePostIds",
					new FormConvertor_IDsToModelList("fdRehirePosts",
							SysOrgPost.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 返聘日期
     */
    public String getFdRehireDate() {
        return this.fdRehireDate;
    }

    /**
     * 返聘日期
     */
    public void setFdRehireDate(String fdRehireDate) {
        this.fdRehireDate = fdRehireDate;
    }

    /**
     * 原入职日期
     */
    public String getFdRehireEnterDate() {
        return this.fdRehireEnterDate;
    }

    /**
     * 原入职日期
     */
    public void setFdRehireEnterDate(String fdRehireEnterDate) {
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
    public String getFdRehireStaffId() {
        return this.fdRehireStaffId;
    }

    /**
     * 返聘员工
     */
    public void setFdRehireStaffId(String fdRehireStaffId) {
        this.fdRehireStaffId = fdRehireStaffId;
    }

    /**
     * 返聘员工
     */
    public String getFdRehireStaffName() {
        return this.fdRehireStaffName;
    }

    /**
     * 返聘员工
     */
    public void setFdRehireStaffName(String fdRehireStaffName) {
        this.fdRehireStaffName = fdRehireStaffName;
    }

    /**
     * 返聘部门
     */
    public String getFdRehireDeptId() {
        return this.fdRehireDeptId;
    }

    /**
     * 返聘部门
     */
    public void setFdRehireDeptId(String fdRehireDeptId) {
        this.fdRehireDeptId = fdRehireDeptId;
    }

    /**
     * 返聘部门
     */
    public String getFdRehireDeptName() {
        return this.fdRehireDeptName;
    }

    /**
     * 返聘部门
     */
    public void setFdRehireDeptName(String fdRehireDeptName) {
        this.fdRehireDeptName = fdRehireDeptName;
    }

    /**
     * 返聘岗位
     */
	public String getFdRehirePostIds() {
		return this.fdRehirePostIds;
    }

    /**
     * 返聘岗位
     */
	public void setFdRehirePostIds(String fdRehirePostIds) {
		this.fdRehirePostIds = fdRehirePostIds;
    }

    /**
     * 返聘岗位
     */
	public String getFdRehirePostNames() {
		return this.fdRehirePostNames;
    }

    /**
     * 返聘岗位
     */
	public void setFdRehirePostNames(String fdRehirePostNames) {
		this.fdRehirePostNames = fdRehirePostNames;
    }
}
