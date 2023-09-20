package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifyRemove;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 合同解除
  */
public class HrRatifyRemoveForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdRemoveBeginDate;

    private String fdRemoveEndDate;

    private String fdIsLongtermContract;

    private String fdRemoveRemark;

    private String fdRemoveCancelDate;

    private String fdRemoveReason;

    private String fdRemoveStaffId;

    private String fdRemoveStaffName;

    private String fdRemoveDeptId;

    private String fdRemoveDeptName;

	private String fdRemoveContractId;

	private String fdRemoveContractName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdRemoveBeginDate = null;
        fdRemoveEndDate = null;
        fdIsLongtermContract = null;
        fdRemoveRemark = null;
        fdRemoveCancelDate = null;
        fdRemoveReason = null;
        fdRemoveStaffId = null;
        fdRemoveStaffName = null;
        fdRemoveDeptId = null;
        fdRemoveDeptName = null;
		fdRemoveContractId = null;
		fdRemoveContractName = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifyRemove.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdRemoveBeginDate",
					new FormConvertor_Common("fdRemoveBeginDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toModelPropertyMap.put("fdRemoveEndDate",
					new FormConvertor_Common("fdRemoveEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdRemoveCancelDate", new FormConvertor_Common("fdRemoveCancelDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdRemoveStaffId", new FormConvertor_IDToModel("fdRemoveStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdRemoveDeptId", new FormConvertor_IDToModel("fdRemoveDept", SysOrgElement.class));
			toModelPropertyMap.put("fdRemoveContractId",
					new FormConvertor_IDToModel("fdRemoveContract",
							HrStaffPersonExperienceContract.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 解除的合同开始时间
     */
    public String getFdRemoveBeginDate() {
        return this.fdRemoveBeginDate;
    }

    /**
     * 解除的合同开始时间
     */
    public void setFdRemoveBeginDate(String fdRemoveBeginDate) {
        this.fdRemoveBeginDate = fdRemoveBeginDate;
    }

    /**
     * 解除的合同结束时间
     */
    public String getFdRemoveEndDate() {
        return this.fdRemoveEndDate;
    }

    /**
     * 解除的合同结束时间
     */
    public void setFdRemoveEndDate(String fdRemoveEndDate) {
        this.fdRemoveEndDate = fdRemoveEndDate;
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
    public String getFdRemoveCancelDate() {
        return this.fdRemoveCancelDate;
    }

    /**
     * 合同解除日期
     */
    public void setFdRemoveCancelDate(String fdRemoveCancelDate) {
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
    public String getFdRemoveStaffId() {
        return this.fdRemoveStaffId;
    }

    /**
     * 合同解除人员
     */
    public void setFdRemoveStaffId(String fdRemoveStaffId) {
        this.fdRemoveStaffId = fdRemoveStaffId;
    }

    /**
     * 合同解除人员
     */
    public String getFdRemoveStaffName() {
        return this.fdRemoveStaffName;
    }

    /**
     * 合同解除人员
     */
    public void setFdRemoveStaffName(String fdRemoveStaffName) {
        this.fdRemoveStaffName = fdRemoveStaffName;
    }

    /**
     * 所属部门
     */
    public String getFdRemoveDeptId() {
        return this.fdRemoveDeptId;
    }

    /**
     * 所属部门
     */
    public void setFdRemoveDeptId(String fdRemoveDeptId) {
        this.fdRemoveDeptId = fdRemoveDeptId;
    }

    /**
     * 所属部门
     */
    public String getFdRemoveDeptName() {
        return this.fdRemoveDeptName;
    }

    /**
     * 所属部门
     */
    public void setFdRemoveDeptName(String fdRemoveDeptName) {
        this.fdRemoveDeptName = fdRemoveDeptName;
    }

	/**
	 * 解除的合同
	 */
	public String getFdRemoveContractId() {
		return fdRemoveContractId;
	}

	/**
	 * 解除的合同
	 */
	public void setFdRemoveContractId(String fdRemoveContractId) {
		this.fdRemoveContractId = fdRemoveContractId;
	}

	/**
	 * 解除的合同
	 */
	public String getFdRemoveContractName() {
		return fdRemoveContractName;
	}

	/**
	 * 解除的合同
	 */
	public void setFdRemoveContractName(String fdRemoveContractName) {
		this.fdRemoveContractName = fdRemoveContractName;
	}

}
