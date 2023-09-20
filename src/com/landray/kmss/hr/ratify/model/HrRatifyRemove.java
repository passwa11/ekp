package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifyRemoveForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 合同解除
  */
public class HrRatifyRemove extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date fdRemoveBeginDate;

    private Date fdRemoveEndDate;

    private Boolean fdIsLongtermContract;

    private String fdRemoveRemark;

    private Date fdRemoveCancelDate;

    private String fdRemoveReason;

    private SysOrgPerson fdRemoveStaff;

    private SysOrgElement fdRemoveDept;

	private HrStaffPersonExperienceContract fdRemoveContract;

	@Override
    public Class getFormClass() {
        return HrRatifyRemoveForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdRemoveBeginDate", new ModelConvertor_Common("fdRemoveBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdRemoveEndDate", new ModelConvertor_Common("fdRemoveEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdRemoveCancelDate", new ModelConvertor_Common("fdRemoveCancelDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdRemoveStaff.fdName", "fdRemoveStaffName");
            toFormPropertyMap.put("fdRemoveStaff.fdId", "fdRemoveStaffId");
            toFormPropertyMap.put("fdRemoveDept.deptLevelNames", "fdRemoveDeptName");
            toFormPropertyMap.put("fdRemoveDept.fdId", "fdRemoveDeptId");
			toFormPropertyMap.put("fdRemoveContract.fdId",
					"fdRemoveContractId");
			toFormPropertyMap.put("fdRemoveContract.fdName",
					"fdRemoveContractName");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 解除的合同开始时间
     */
    public Date getFdRemoveBeginDate() {
        return this.fdRemoveBeginDate;
    }

    /**
     * 解除的合同开始时间
     */
    public void setFdRemoveBeginDate(Date fdRemoveBeginDate) {
        this.fdRemoveBeginDate = fdRemoveBeginDate;
    }

    /**
     * 解除的合同结束时间
     */
    public Date getFdRemoveEndDate() {
        return this.fdRemoveEndDate;
    }

    /**
     * 解除的合同结束时间
     */
    public void setFdRemoveEndDate(Date fdRemoveEndDate) {
        this.fdRemoveEndDate = fdRemoveEndDate;
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
     * 解除的合同备注
     */
    public String getFdRemoveRemark() {
		return this.fdRemoveRemark;
    }

    /**
     * 解除的合同备注
     */
    public void setFdRemoveRemark(String fdRemoveRemark) {
		this.fdRemoveRemark = fdRemoveRemark;
    }

    /**
     * 合同解除日期
     */
    public Date getFdRemoveCancelDate() {
        return this.fdRemoveCancelDate;
    }

    /**
     * 合同解除日期
     */
    public void setFdRemoveCancelDate(Date fdRemoveCancelDate) {
        this.fdRemoveCancelDate = fdRemoveCancelDate;
    }

    /**
     * 合同解除原因
     */
    public String getFdRemoveReason() {
		return this.fdRemoveReason;
    }

    /**
     * 合同解除原因
     */
    public void setFdRemoveReason(String fdRemoveReason) {
		this.fdRemoveReason = fdRemoveReason;
    }

    /**
     * 合同解除人员
     */
    public SysOrgPerson getFdRemoveStaff() {
        return this.fdRemoveStaff;
    }

    /**
     * 合同解除人员
     */
    public void setFdRemoveStaff(SysOrgPerson fdRemoveStaff) {
        this.fdRemoveStaff = fdRemoveStaff;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getFdRemoveDept() {
        return this.fdRemoveDept;
    }

    /**
     * 所属部门
     */
    public void setFdRemoveDept(SysOrgElement fdRemoveDept) {
        this.fdRemoveDept = fdRemoveDept;
    }

	/**
	 * 解除的合同
	 */
	public HrStaffPersonExperienceContract getFdRemoveContract() {
		return fdRemoveContract;
	}

	/**
	 * 解除的合同
	 */
	public void
			setFdRemoveContract(
					HrStaffPersonExperienceContract fdRemoveContract) {
		this.fdRemoveContract = fdRemoveContract;
	}

}
