package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffCertifiForm;
import com.landray.kmss.util.DateUtil;

public class HrStaffCertifi extends BaseModel {

	private static ModelToFormPropertyMap toFormPropertyMap;

	private String fdName;

	private String fdIssuingUnit;

	private Date fdIssueDate;

	private Date fdInvalidDate;

	private String fdRemark;

	private HrStaffEntry docMain;

	private Integer docIndex;

	@Override
    public Class<HrStaffCertifiForm> getFormClass() {
		return HrStaffCertifiForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdIssueDate",
					new ModelConvertor_Common("fdIssueDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdInvalidDate",
					new ModelConvertor_Common("fdInvalidDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("docMain.fdName", "docMainName");
			toFormPropertyMap.put("docMain.fdId", "docMainId");
		}
		return toFormPropertyMap;
	}

	/**
	 * 证书名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 证书名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 颁发单位
	 */
	public String getFdIssuingUnit() {
		return this.fdIssuingUnit;
	}

	/**
	 * 颁发单位
	 */
	public void setFdIssuingUnit(String fdIssuingUnit) {
		this.fdIssuingUnit = fdIssuingUnit;
	}

	/**
	 * 颁发日期
	 */
	public Date getFdIssueDate() {
		return this.fdIssueDate;
	}

	/**
	 * 颁发日期
	 */
	public void setFdIssueDate(Date fdIssueDate) {
		this.fdIssueDate = fdIssueDate;
	}

	/**
	 * 失效日期
	 */
	public Date getFdInvalidDate() {
		return this.fdInvalidDate;
	}

	/**
	 * 失效日期
	 */
	public void setFdInvalidDate(Date fdInvalidDate) {
		this.fdInvalidDate = fdInvalidDate;
	}

	/**
	 * 备注
	 */
	public String getFdRemark() {
		return this.fdRemark;
	}

	/**
	 * 备注
	 */
	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}

	public HrStaffEntry getDocMain() {
		return this.docMain;
	}

	public void setDocMain(HrStaffEntry docMain) {
		this.docMain = docMain;
	}

	public Integer getDocIndex() {
		return this.docIndex;
	}

	public void setDocIndex(Integer docIndex) {
		this.docIndex = docIndex;
	}
}
