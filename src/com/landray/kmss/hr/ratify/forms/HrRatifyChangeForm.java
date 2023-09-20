package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyChange;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 合同变更
  */
public class HrRatifyChangeForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdChangeSignBeginDate;

    private String fdChangeSignEndDate;

    private String fdChangeSignRemark;

    private String fdChangeBeginDate;

    private String fdChangeEndDate;

    private String fdChangeIsLongtermContract;

    private String fdIsLongtermContract;

    private String fdChangeRemark;

    private String fdChangeReason;

    private String fdChangeStaffId;

    private String fdChangeStaffName;

    private String fdChangeDeptId;

    private String fdChangeDeptName;

	private String fdContractId;

	private String fdContractName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdContractId = null;
		fdContractName = null;
        fdChangeSignBeginDate = null;
        fdChangeSignEndDate = null;
        fdChangeSignRemark = null;
        fdChangeBeginDate = null;
        fdChangeEndDate = null;
        fdChangeIsLongtermContract = null;
        fdIsLongtermContract = null;
        fdChangeRemark = null;
        fdChangeReason = null;
        fdChangeStaffId = null;
        fdChangeStaffName = null;
        fdChangeDeptId = null;
        fdChangeDeptName = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyChange.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdChangeSignBeginDate",
					new FormConvertor_Common("fdChangeSignBeginDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdChangeSignEndDate",
					new FormConvertor_Common("fdChangeSignEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdChangeBeginDate", new FormConvertor_Common("fdChangeBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdChangeEndDate", new FormConvertor_Common("fdChangeEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdChangeStaffId", new FormConvertor_IDToModel("fdChangeStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdChangeDeptId", new FormConvertor_IDToModel("fdChangeDept", SysOrgElement.class));
			toModelPropertyMap.put("fdContractId", new FormConvertor_IDToModel(
					"fdContract", HrStaffPersonExperienceContract.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 原合同开始时间
     */
    public String getFdChangeSignBeginDate() {
        return this.fdChangeSignBeginDate;
    }

    /**
     * 原合同开始时间
     */
    public void setFdChangeSignBeginDate(String fdChangeSignBeginDate) {
        this.fdChangeSignBeginDate = fdChangeSignBeginDate;
    }

    /**
     * 原合同结束时间
     */
    public String getFdChangeSignEndDate() {
        return this.fdChangeSignEndDate;
    }

    /**
     * 原合同结束时间
     */
    public void setFdChangeSignEndDate(String fdChangeSignEndDate) {
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
    public String getFdChangeBeginDate() {
        return this.fdChangeBeginDate;
    }

    /**
     * 变更后合同开始时间
     */
    public void setFdChangeBeginDate(String fdChangeBeginDate) {
        this.fdChangeBeginDate = fdChangeBeginDate;
    }

    /**
     * 变更后合同到期时间
     */
    public String getFdChangeEndDate() {
        return this.fdChangeEndDate;
    }

    /**
     * 变更后合同到期时间
     */
    public void setFdChangeEndDate(String fdChangeEndDate) {
        this.fdChangeEndDate = fdChangeEndDate;
    }

    /**
     * 原合同长期合同
     */
    public String getFdChangeIsLongtermContract() {
        return fdChangeIsLongtermContract;
    }

    /**
     * 原合同长期合同
     */
    public void setFdChangeIsLongtermContract(String fdChangeIsLongtermContract) {
        this.fdChangeIsLongtermContract = fdChangeIsLongtermContract;
    }

    /**
     * 长期合同
     */
    public String getFdIsLongtermContract() {
        if("true".equals(this.fdIsLongtermContract)){
            return fdIsLongtermContract;
        }
        return null;
    }

    /**
     * 长期合同
     */
    public void setFdIsLongtermContract(String fdIsLongtermContract) {
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
        this.fdChangeRemark = fdChangeRemark;
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
    public String getFdChangeStaffId() {
        return this.fdChangeStaffId;
    }

    /**
     * 合同变更人员
     */
    public void setFdChangeStaffId(String fdChangeStaffId) {
        this.fdChangeStaffId = fdChangeStaffId;
    }

    /**
     * 合同变更人员
     */
    public String getFdChangeStaffName() {
        return this.fdChangeStaffName;
    }

    /**
     * 合同变更人员
     */
    public void setFdChangeStaffName(String fdChangeStaffName) {
        this.fdChangeStaffName = fdChangeStaffName;
    }

    /**
     * 所属部门
     */
    public String getFdChangeDeptId() {
        return this.fdChangeDeptId;
    }

    /**
     * 所属部门
     */
    public void setFdChangeDeptId(String fdChangeDeptId) {
        this.fdChangeDeptId = fdChangeDeptId;
    }

    /**
     * 所属部门
     */
    public String getFdChangeDeptName() {
        return this.fdChangeDeptName;
    }

    /**
     * 所属部门
     */
    public void setFdChangeDeptName(String fdChangeDeptName) {
        this.fdChangeDeptName = fdChangeDeptName;
    }

	/**
	 * 原合同
	 */
	public String getFdContractId() {
		return fdContractId;
	}

	/**
	 * 原合同
	 */
	public void setFdContractId(String fdContractId) {
		this.fdContractId = fdContractId;
	}

	/**
	 * 原合同
	 */
	public String getFdContractName() {
		return fdContractName;
	}

	/**
	 * 原合同
	 */
	public void setFdContractName(String fdContractName) {
		this.fdContractName = fdContractName;
	}

}
