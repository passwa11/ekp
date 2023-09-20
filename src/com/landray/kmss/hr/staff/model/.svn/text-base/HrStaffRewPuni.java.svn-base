package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffRewPuniForm;
import com.landray.kmss.util.DateUtil;

public class HrStaffRewPuni extends BaseModel {

	private static ModelToFormPropertyMap toFormPropertyMap;

	private String fdName;

	private Date fdDate;

	private String fdRemark;

	private HrStaffEntry docMain;

	private Integer docIndex;

	@Override
    public Class<HrStaffRewPuniForm> getFormClass() {
		return HrStaffRewPuniForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdDate", new ModelConvertor_Common("fdDate")
					.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("docMain.fdName", "docMainName");
			toFormPropertyMap.put("docMain.fdId", "docMainId");
		}
		return toFormPropertyMap;
	}

	/**
	 * 奖惩名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 奖惩名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 奖惩日期
	 */
	public Date getFdDate() {
		return this.fdDate;
	}

	/**
	 * 奖惩日期
	 */
	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
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
