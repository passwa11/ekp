package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyPositive;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 员工转正
  */
public class HrRatifyPositiveForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdPositiveEnterDate;

    private String fdPositiveTrialPeriod;

	private String fdPositivePeriodDate;

    private String fdPositiveFormalDate;

    private String fdPositivePostThink;

    private String fdPositiveTrialSummary;

    private String fdPositiveSelfEval;

    private String fdPositiveTeacherView;

    private String fdPositiveLeaderView;

    private String fdPositiveHrView;

    private String fdPositiveRemark;

    private String fdPositiveStaffId;

    private String fdPositiveStaffName;

    private String fdPositiveDeptId;

    private String fdPositiveDeptName;

    private String fdPositiveLeaderId;

    private String fdPositiveLeaderName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdPositiveEnterDate = null;
        fdPositiveTrialPeriod = null;
		fdPositivePeriodDate = null;
        fdPositiveFormalDate = null;
        fdPositivePostThink = null;
        fdPositiveTrialSummary = null;
        fdPositiveSelfEval = null;
        fdPositiveTeacherView = null;
        fdPositiveLeaderView = null;
        fdPositiveHrView = null;
        fdPositiveRemark = null;
        fdPositiveStaffId = null;
        fdPositiveStaffName = null;
        fdPositiveDeptId = null;
        fdPositiveDeptName = null;
        fdPositiveLeaderId = null;
        fdPositiveLeaderName = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyPositive.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdPositiveEnterDate", new FormConvertor_Common("fdPositiveEnterDate").setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdPositivePeriodDate",
					new FormConvertor_Common("fdPositivePeriodDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdPositiveFormalDate", new FormConvertor_Common("fdPositiveFormalDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdPositiveStaffId", new FormConvertor_IDToModel("fdPositiveStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdPositiveDeptId", new FormConvertor_IDToModel("fdPositiveDept", SysOrgElement.class));
            toModelPropertyMap.put("fdPositiveLeaderId", new FormConvertor_IDToModel("fdPositiveLeader", SysOrgPerson.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 入职日期
     */
    public String getFdPositiveEnterDate() {
        return this.fdPositiveEnterDate;
    }

    /**
     * 入职日期
     */
    public void setFdPositiveEnterDate(String fdPositiveEnterDate) {
        this.fdPositiveEnterDate = fdPositiveEnterDate;
    }

    /**
     * 试用期限（月）
     */
    public String getFdPositiveTrialPeriod() {
        return this.fdPositiveTrialPeriod;
    }

    /**
     * 试用期限（月）
     */
    public void setFdPositiveTrialPeriod(String fdPositiveTrialPeriod) {
        this.fdPositiveTrialPeriod = fdPositiveTrialPeriod;
    }

	/**
	 * 拟转正日期
	 */
	public String getFdPositivePeriodDate() {
		return this.fdPositivePeriodDate;
	}

	/**
	 * 拟转正日期
	 */
	public void setFdPositivePeriodDate(String fdPositivePeriodDate) {
		this.fdPositivePeriodDate = fdPositivePeriodDate;
	}

    /**
	 * 批准转正日期
	 */
    public String getFdPositiveFormalDate() {
        return this.fdPositiveFormalDate;
    }

    /**
	 * 批准转正日期
	 */
    public void setFdPositiveFormalDate(String fdPositiveFormalDate) {
        this.fdPositiveFormalDate = fdPositiveFormalDate;
    }

    /**
     * 对本岗位的理解
     */
    public String getFdPositivePostThink() {
        return this.fdPositivePostThink;
    }

    /**
     * 对本岗位的理解
     */
    public void setFdPositivePostThink(String fdPositivePostThink) {
        this.fdPositivePostThink = fdPositivePostThink;
    }

    /**
     * 试用期内工作总结
     */
    public String getFdPositiveTrialSummary() {
        return this.fdPositiveTrialSummary;
    }

    /**
     * 试用期内工作总结
     */
    public void setFdPositiveTrialSummary(String fdPositiveTrialSummary) {
        this.fdPositiveTrialSummary = fdPositiveTrialSummary;
    }

    /**
     * 员工自我评价
     */
    public String getFdPositiveSelfEval() {
        return this.fdPositiveSelfEval;
    }

    /**
     * 员工自我评价
     */
    public void setFdPositiveSelfEval(String fdPositiveSelfEval) {
        this.fdPositiveSelfEval = fdPositiveSelfEval;
    }

    /**
     * 导师意见
     */
    public String getFdPositiveTeacherView() {
        return this.fdPositiveTeacherView;
    }

    /**
     * 导师意见
     */
    public void setFdPositiveTeacherView(String fdPositiveTeacherView) {
        this.fdPositiveTeacherView = fdPositiveTeacherView;
    }

    /**
     * 部门领导意见
     */
    public String getFdPositiveLeaderView() {
        return this.fdPositiveLeaderView;
    }

    /**
     * 部门领导意见
     */
    public void setFdPositiveLeaderView(String fdPositiveLeaderView) {
        this.fdPositiveLeaderView = fdPositiveLeaderView;
    }

    /**
     * 人力资源部门意见
     */
    public String getFdPositiveHrView() {
        return this.fdPositiveHrView;
    }

    /**
     * 人力资源部门意见
     */
    public void setFdPositiveHrView(String fdPositiveHrView) {
        this.fdPositiveHrView = fdPositiveHrView;
    }

    /**
     * 备注
     */
    public String getFdPositiveRemark() {
        return this.fdPositiveRemark;
    }

    /**
     * 备注
     */
    public void setFdPositiveRemark(String fdPositiveRemark) {
        this.fdPositiveRemark = fdPositiveRemark;
    }

    /**
     * 转正人员
     */
    public String getFdPositiveStaffId() {
        return this.fdPositiveStaffId;
    }

    /**
     * 转正人员
     */
    public void setFdPositiveStaffId(String fdPositiveStaffId) {
        this.fdPositiveStaffId = fdPositiveStaffId;
    }

    /**
     * 转正人员
     */
    public String getFdPositiveStaffName() {
        return this.fdPositiveStaffName;
    }

    /**
     * 转正人员
     */
    public void setFdPositiveStaffName(String fdPositiveStaffName) {
        this.fdPositiveStaffName = fdPositiveStaffName;
    }

    /**
     * 所属部门
     */
    public String getFdPositiveDeptId() {
        return this.fdPositiveDeptId;
    }

    /**
     * 所属部门
     */
    public void setFdPositiveDeptId(String fdPositiveDeptId) {
        this.fdPositiveDeptId = fdPositiveDeptId;
    }

    /**
     * 所属部门
     */
    public String getFdPositiveDeptName() {
        return this.fdPositiveDeptName;
    }

    /**
     * 所属部门
     */
    public void setFdPositiveDeptName(String fdPositiveDeptName) {
        this.fdPositiveDeptName = fdPositiveDeptName;
    }

    /**
     * 直接上级
     */
    public String getFdPositiveLeaderId() {
        return this.fdPositiveLeaderId;
    }

    /**
     * 直接上级
     */
    public void setFdPositiveLeaderId(String fdPositiveLeaderId) {
        this.fdPositiveLeaderId = fdPositiveLeaderId;
    }

    /**
     * 直接上级
     */
    public String getFdPositiveLeaderName() {
        return this.fdPositiveLeaderName;
    }

    /**
     * 直接上级
     */
    public void setFdPositiveLeaderName(String fdPositiveLeaderName) {
        this.fdPositiveLeaderName = fdPositiveLeaderName;
    }
}
