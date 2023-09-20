package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyPositiveForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 员工转正
  */
public class HrRatifyPositive extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdPositiveEnterDate;

    private String fdPositiveTrialPeriod;

	private Date fdPositivePeriodDate;

    private Date fdPositiveFormalDate;

    private String fdPositivePostThink;

    private String fdPositiveTrialSummary;

    private String fdPositiveSelfEval;

    private String fdPositiveTeacherView;

    private String fdPositiveLeaderView;

    private String fdPositiveHrView;

    private String fdPositiveRemark;

    private SysOrgPerson fdPositiveStaff;

    private SysOrgElement fdPositiveDept;

    private SysOrgPerson fdPositiveLeader;

	@Override
    public Class getFormClass() {
        return HrRatifyPositiveForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdPositiveEnterDate", new ModelConvertor_Common("fdPositiveEnterDate").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdPositivePeriodDate",
					new ModelConvertor_Common("fdPositivePeriodDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdPositiveFormalDate", new ModelConvertor_Common("fdPositiveFormalDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdPositiveStaff.fdName", "fdPositiveStaffName");
            toFormPropertyMap.put("fdPositiveStaff.fdId", "fdPositiveStaffId");
            toFormPropertyMap.put("fdPositiveDept.deptLevelNames", "fdPositiveDeptName");
            toFormPropertyMap.put("fdPositiveDept.fdId", "fdPositiveDeptId");
            toFormPropertyMap.put("fdPositiveLeader.fdName", "fdPositiveLeaderName");
            toFormPropertyMap.put("fdPositiveLeader.fdId", "fdPositiveLeaderId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 入职日期
     */
    public Date getFdPositiveEnterDate() {
        return this.fdPositiveEnterDate;
    }

    /**
     * 入职日期
     */
    public void setFdPositiveEnterDate(Date fdPositiveEnterDate) {
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
	public Date getFdPositivePeriodDate() {
		return this.fdPositivePeriodDate;
	}

	/**
	 * 拟转正日期
	 */
	public void setFdPositivePeriodDate(Date fdPositivePeriodDate) {
		this.fdPositivePeriodDate = fdPositivePeriodDate;
	}

    /**
	 * 批准转正日期
	 */
    public Date getFdPositiveFormalDate() {
        return this.fdPositiveFormalDate;
    }

    /**
	 * 批准转正日期
	 */
    public void setFdPositiveFormalDate(Date fdPositiveFormalDate) {
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
        return (String) readLazyField("fdPositiveTrialSummary", this.fdPositiveTrialSummary);
    }

    /**
     * 试用期内工作总结
     */
    public void setFdPositiveTrialSummary(String fdPositiveTrialSummary) {
        this.fdPositiveTrialSummary = (String) writeLazyField("fdPositiveTrialSummary", this.fdPositiveTrialSummary, fdPositiveTrialSummary);
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
    public SysOrgPerson getFdPositiveStaff() {
        return this.fdPositiveStaff;
    }

    /**
     * 转正人员
     */
    public void setFdPositiveStaff(SysOrgPerson fdPositiveStaff) {
        this.fdPositiveStaff = fdPositiveStaff;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getFdPositiveDept() {
        return this.fdPositiveDept;
    }

    /**
     * 所属部门
     */
    public void setFdPositiveDept(SysOrgElement fdPositiveDept) {
        this.fdPositiveDept = fdPositiveDept;
    }

    /**
     * 直接上级
     */
    public SysOrgPerson getFdPositiveLeader() {
        return this.fdPositiveLeader;
    }

    /**
     * 直接上级
     */
    public void setFdPositiveLeader(SysOrgPerson fdPositiveLeader) {
        this.fdPositiveLeader = fdPositiveLeader;
    }
}
