package com.landray.kmss.hr.ratify.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.ratify.forms.HrRatifySignForm;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;

import java.util.Date;

/**
  * 合同签订
  */
public class HrRatifySign extends HrRatifyMain {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdSignName;

    private String fdSignContType;
	private HrStaffContractType fdSignStaffContType;

    private String fdSignType;

    private Date fdSignBeginDate;

    private Date fdSignEndDate;

    private Boolean fdIsLongtermContract;

    private Date fdSignHandleDate;

    private String fdSignRemark;

    private SysOrgPerson fdSignStaff;

    private SysOrgElement fdSignDept;

	@Override
    public Class getFormClass() {
        return HrRatifySignForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdSignBeginDate", new ModelConvertor_Common("fdSignBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdSignEndDate", new ModelConvertor_Common("fdSignEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdSignHandleDate", new ModelConvertor_Common("fdSignHandleDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdSignStaff.fdName", "fdSignStaffName");
            toFormPropertyMap.put("fdSignStaff.fdId", "fdSignStaffId");
            toFormPropertyMap.put("fdSignDept.deptLevelNames", "fdSignDeptName");
            toFormPropertyMap.put("fdSignDept.fdId", "fdSignDeptId");
			toFormPropertyMap.put("fdSignStaffContType.fdId",
					"fdSignStaffContTypeId");
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 合同名称
     */
    public String getFdSignName() {
        return this.fdSignName;
    }

    /**
     * 合同名称
     */
    public void setFdSignName(String fdSignName) {
        this.fdSignName = fdSignName;
    }

    /**
     * 合同类型
     */
    public String getFdSignContType() {
        return this.fdSignContType;
    }

    /**
     * 合同类型
     */
    public void setFdSignContType(String fdSignContType) {
        this.fdSignContType = fdSignContType;
    }

	public HrStaffContractType getFdSignStaffContType() {
		return fdSignStaffContType;
	}

	public void
			setFdSignStaffContType(HrStaffContractType fdSignStaffContType) {
		this.fdSignStaffContType = fdSignStaffContType;
	}

	/**
	 * 签订标识
	 */
    public String getFdSignType() {
        return this.fdSignType;
    }

    /**
     * 签订标识
     */
    public void setFdSignType(String fdSignType) {
        this.fdSignType = fdSignType;
    }

    /**
     * 合同开始时间
     */
    public Date getFdSignBeginDate() {
        return this.fdSignBeginDate;
    }

    /**
     * 合同开始时间
     */
    public void setFdSignBeginDate(Date fdSignBeginDate) {
        this.fdSignBeginDate = fdSignBeginDate;
    }

    /**
     * 合同到期时间
     */
    public Date getFdSignEndDate() {
        return this.fdSignEndDate;
    }

    /**
     * 合同到期时间
     */
    public void setFdSignEndDate(Date fdSignEndDate) {
        this.fdSignEndDate = fdSignEndDate;
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
     * 合同办理日期
     */
    public Date getFdSignHandleDate() {
        return this.fdSignHandleDate;
    }

    /**
     * 合同办理日期
     */
    public void setFdSignHandleDate(Date fdSignHandleDate) {
        this.fdSignHandleDate = fdSignHandleDate;
    }

    /**
     * 合同备注
     */
    public String getFdSignRemark() {
		return this.fdSignRemark;
    }

    /**
     * 合同备注
     */
    public void setFdSignRemark(String fdSignRemark) {
		this.fdSignRemark = fdSignRemark;
    }

    /**
     * 合同签订人员
     */
    public SysOrgPerson getFdSignStaff() {
        return this.fdSignStaff;
    }

    /**
     * 合同签订人员
     */
    public void setFdSignStaff(SysOrgPerson fdSignStaff) {
        this.fdSignStaff = fdSignStaff;
    }

    /**
     * 所属部门
     */
    public SysOrgElement getFdSignDept() {
        return this.fdSignDept;
    }

    /**
     * 所属部门
     */
    public void setFdSignDept(SysOrgElement fdSignDept) {
        this.fdSignDept = fdSignDept;
    }
    
    /**
	 * 是否启用电子签章
	 */

	private Boolean fdSignEnable;

	/**
	 * 是否启用电子签章
	 */
	public Boolean getFdSignEnable() {
		if (fdSignEnable == null) {
			return false;
		}
		return fdSignEnable;
	}

	/**
	 * 是否启用电子签章
	 */
	public void setFdSignEnable(Boolean fdSignEnable) {
		this.fdSignEnable = fdSignEnable;
	}

}
