package com.landray.kmss.hr.ratify.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyCertifiForm;
import com.landray.kmss.util.DateUtil;

/**
 * 资格证书
 */
public class HrRatifyCertifi extends BaseModel {

	private static ModelToFormPropertyMap toFormPropertyMap;

	private String fdName;

	private String fdIssuingUnit;

	private Date fdIssueDate;

	private Date fdInvalidDate;

	private String fdRemark;

	private HrRatifyEntry docMain;

	private Integer docIndex;

	@Override
    public Class<HrRatifyCertifiForm> getFormClass() {
		return HrRatifyCertifiForm.class;
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
			toFormPropertyMap.put("docMain.docSubject", "docMainName");
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

	public HrRatifyEntry getDocMain() {
		return this.docMain;
	}

	public void setDocMain(HrRatifyEntry docMain) {
		this.docMain = docMain;
	}

	public Integer getDocIndex() {
		return this.docIndex;
	}

	public void setDocIndex(Integer docIndex) {
		this.docIndex = docIndex;
	}

}
