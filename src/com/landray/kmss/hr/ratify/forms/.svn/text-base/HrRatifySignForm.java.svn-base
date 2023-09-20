package com.landray.kmss.hr.ratify.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.ratify.model.HrRatifySign;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 合同签订
  */
public class HrRatifySignForm extends HrRatifyMainForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdSignName;

    private String fdSignContType;
	private String fdSignStaffContTypeId;

    private String fdSignType;

    private String fdSignBeginDate;

    private String fdSignEndDate;

    private String fdIsLongtermContract;

    private String fdSignHandleDate;

    private String fdSignRemark;

    private String fdSignStaffId;

    private String fdSignStaffName;

    private String fdSignDeptId;

    private String fdSignDeptName;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdSignName = null;
        fdSignContType = null;
		fdSignStaffContTypeId = null;
        fdSignType = null;
        fdSignBeginDate = null;
        fdSignEndDate = null;
        fdIsLongtermContract = null;
        fdSignHandleDate = null;
        fdSignRemark = null;
        fdSignStaffId = null;
        fdSignStaffName = null;
        fdSignDeptId = null;
        fdSignDeptName = null;
        super.reset(mapping, request);
    }

	@Override
    public Class getModelClass() {
        return HrRatifySign.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdSignBeginDate", new FormConvertor_Common("fdSignBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdSignEndDate", new FormConvertor_Common("fdSignEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdSignHandleDate", new FormConvertor_Common("fdSignHandleDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdSignStaffId", new FormConvertor_IDToModel("fdSignStaff", SysOrgPerson.class));
            toModelPropertyMap.put("fdSignDeptId", new FormConvertor_IDToModel("fdSignDept", SysOrgElement.class));
			toModelPropertyMap.put("fdSignStaffContTypeId",
					new FormConvertor_IDToModel("fdSignStaffContType",
							HrStaffContractType.class));
        }
        return toModelPropertyMap;
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

	public String getFdSignStaffContTypeId() {
		return fdSignStaffContTypeId;
	}

	public void setFdSignStaffContTypeId(String fdSignStaffContTypeId) {
		this.fdSignStaffContTypeId = fdSignStaffContTypeId;
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
    public String getFdSignBeginDate() {
        return this.fdSignBeginDate;
    }

    /**
     * 合同开始时间
     */
    public void setFdSignBeginDate(String fdSignBeginDate) {
        this.fdSignBeginDate = fdSignBeginDate;
    }

    /**
     * 合同到期时间
     */
    public String getFdSignEndDate() {
        return this.fdSignEndDate;
    }

    /**
     * 合同到期时间
     */
    public void setFdSignEndDate(String fdSignEndDate) {
        this.fdSignEndDate = fdSignEndDate;
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
     * 合同办理日期
     */
    public String getFdSignHandleDate() {
        return this.fdSignHandleDate;
    }

    /**
     * 合同办理日期
     */
    public void setFdSignHandleDate(String fdSignHandleDate) {
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
    public String getFdSignStaffId() {
        return this.fdSignStaffId;
    }

    /**
     * 合同签订人员
     */
    public void setFdSignStaffId(String fdSignStaffId) {
        this.fdSignStaffId = fdSignStaffId;
    }

    /**
     * 合同签订人员
     */
    public String getFdSignStaffName() {
        return this.fdSignStaffName;
    }

    /**
     * 合同签订人员
     */
    public void setFdSignStaffName(String fdSignStaffName) {
        this.fdSignStaffName = fdSignStaffName;
    }

    /**
     * 所属部门
     */
    public String getFdSignDeptId() {
        return this.fdSignDeptId;
    }

    /**
     * 所属部门
     */
    public void setFdSignDeptId(String fdSignDeptId) {
        this.fdSignDeptId = fdSignDeptId;
    }

    /**
     * 所属部门
     */
    public String getFdSignDeptName() {
        return this.fdSignDeptName;
    }

    /**
     * 所属部门
     */
    public void setFdSignDeptName(String fdSignDeptName) {
        this.fdSignDeptName = fdSignDeptName;
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
