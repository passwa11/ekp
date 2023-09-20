package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyChangeForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 合同变更
  */
public class HrRatifyChange extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdChangeSignBeginDate;

    private Date fdChangeSignEndDate;

    private String fdChangeSignRemark;

    private Date fdChangeBeginDate;

    private Date fdChangeEndDate;

    private Boolean fdChangeIsLongtermContract;

    private Boolean fdIsLongtermContract;

    private String fdChangeRemark;

    private String fdChangeReason;

    private SysOrgPerson fdChangeStaff;

    private SysOrgElement fdChangeDept;

	private HrStaffPersonExperienceContract fdContract;

	@Override
    public Class getFormClass() {
        return HrRatifyChangeForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdChangeSignBeginDate", new ModelConvertor_Common("fdChangeSignBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdChangeSignEndDate", new ModelConvertor_Common("fdChangeSignEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdChangeBeginDate", new ModelConvertor_Common("fdChangeBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdChangeEndDate", new ModelConvertor_Common("fdChangeEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdChangeStaff.fdName", "fdChangeStaffName");
            toFormPropertyMap.put("fdChangeStaff.fdId", "fdChangeStaffId");
            toFormPropertyMap.put("fdChangeDept.deptLevelNames", "fdChangeDeptName");
            toFormPropertyMap.put("fdChangeDept.fdId", "fdChangeDeptId");
			toFormPropertyMap.put("fdContract.fdId", "fdContractId");
			toFormPropertyMap.put("fdContract.fdName", "fdContractName");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 原合同开始时间
     */
    public Date getFdChangeSignBeginDate() {
        return this.fdChangeSignBeginDate;
    }

    /**
     * 原合同开始时间
     */
    public void setFdChangeSignBeginDate(Date fdChangeSignBeginDate) {
        this.fdChangeSignBeginDate = fdChangeSignBeginDate;
    }

    /**
     * 原合同结束时间
     */
    public Date getFdChangeSignEndDate() {
        return this.fdChangeSignEndDate;
    }

    /**
     * 原合同结束时间
     */
    public void setFdChangeSignEndDate(Date fdChangeSignEndDate) {
        this.fdChangeSignEndDate = fdChangeSignEndDate;
    }

    /**
     * 原合同备注
     */
    public String getFdChangeSignRemark() {
		return this.fdChangeSignRemark;
    }

    /**
     * 原合同备注
     */
    public void setFdChangeSignRemark(String fdChangeSignRemark) {
		this.fdChangeSignRemark = fdChangeSignRemark;
    }

    /**
     * 变更后合同开始时间
     */
    public Date getFdChangeBeginDate() {
        return this.fdChangeBeginDate;
    }

    /**
     * 变更后合同开始时间
     */
    public void setFdChangeBeginDate(Date fdChangeBeginDate) {
        this.fdChangeBeginDate = fdChangeBeginDate;
    }

    /**
     * 变更后合同到期时间
     */
    public Date getFdChangeEndDate() {
        return this.fdChangeEndDate;
    }

    /**
     * 变更后合同到期时间
     */
    public void setFdChangeEndDate(Date fdChangeEndDate) {
        this.fdChangeEndDate = fdChangeEndDate;
    }

    /**
     * 原合同长期合同
     */
    public Boolean getFdChangeIsLongtermContract() {
        return fdChangeIsLongtermContract;
    }

    /**
     * 原合同长期合同
     */
    public void setFdChangeIsLongtermContract(Boolean fdChangeIsLongtermContract) {
        this.fdChangeIsLongtermContract = fdChangeIsLongtermContract;
    }

    /**
     * 长期合同
     */
    public Boolean getFdIsLongtermContract() {
        return fdIsLongtermContract;
    }

    /**
     * 长期合同
     */
    public void setFdIsLongtermContract(Boolean fdIsLongtermContract) {
        this.fdIsLongtermContract = fdIsLongtermContract;
    }

    /**
     * 变更后合同备注
     */
    public String getFdChangeRemark() {
		return this.fdChangeRemark;
    }

    /**
     * 变更后合同备注
     */
    public void setFdChangeRemark(String fdChangeRemark) {
		this.fdChangeRemark =  fdChangeRemark;
    }

    /**
     * 合同变更原因
     */
    public String getFdChangeReason() {
		return this.fdChangeReason;
    }

    /**
     * 合同变更原因
     */
    public void setFdChangeReason(String fdChangeReason) {
		this.fdChangeReason = fdChangeReason;
    }

    /**
     * 合同变更人员
     */
    public SysOrgPerson getFdChangeStaff() {
        return this.fdChangeStaff;
    }

    /**
     * 合同变更人员
     */
    public void setFdChangeStaff(SysOrgPerson fdChangeStaff) {
        this.fdChangeStaff = fdChangeStaff;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getFdChangeDept() {
        return this.fdChangeDept;
    }

    /**
     * 所属部门
     */
    public void setFdChangeDept(SysOrgElement fdChangeDept) {
        this.fdChangeDept = fdChangeDept;
    }

	/**
	 * 原合同
	 */
	public HrStaffPersonExperienceContract getFdContract() {
		return fdContract;
	}

	/**
	 * 原合同
	 */
	public void setFdContract(HrStaffPersonExperienceContract fdContract) {
		this.fdContract = fdContract;
	}

}
