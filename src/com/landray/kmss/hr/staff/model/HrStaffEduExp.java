package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffEduExpForm;
import com.landray.kmss.util.DateUtil;

public class HrStaffEduExp extends BaseModel {

	private static ModelToFormPropertyMap toFormPropertyMap;
	private HrStaffPersonInfoSettingNew fdEducation;
	private HrStaffPersonInfoSettingNew fdAcadeRecord;

	private String fdName;

	private String fdMajor;

	private Date fdEntranceDate;

	private Date fdGraduationDate;

	private String fdRemark;

	private HrStaffEntry docMain;

	private Integer docIndex;

	@Override
	public Class<HrStaffEduExpForm> getFormClass() {
		return HrStaffEduExpForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdEntranceDate",
					new ModelConvertor_Common("fdEntranceDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdGraduationDate",
					new ModelConvertor_Common("fdGraduationDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("docMain.fdName", "docMainName");
			toFormPropertyMap.put("docMain.fdId", "docMainId");
			toFormPropertyMap.put("fdAcadeRecord.fdName", "fdAcadeRecordName");
			toFormPropertyMap.put("fdAcadeRecord.fdId", "fdAcadeRecordId");
		}
		return toFormPropertyMap;
	}

	/**
	 * 学位
	 */
	public HrStaffPersonInfoSettingNew getFdAcadeRecord() {
		return this.fdAcadeRecord;
	}

	/**
	 * 学位
	 */
	public void setFdAcadeRecord(HrStaffPersonInfoSettingNew fdAcadeRecord) {
		this.fdAcadeRecord = fdAcadeRecord;
	}

	/**
	 * 学校名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 学校名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 专业名称
	 */
	public String getFdMajor() {
		return this.fdMajor;
	}

	/**
	 * 专业名称
	 */
	public void setFdMajor(String fdMajor) {
		this.fdMajor = fdMajor;
	}

	/**
	 * 入学日期
	 */
	public Date getFdEntranceDate() {
		return this.fdEntranceDate;
	}

	/**
	 * 入学日期
	 */
	public void setFdEntranceDate(Date fdEntranceDate) {
		this.fdEntranceDate = fdEntranceDate;
	}

	/**
	 * 毕业日期
	 */
	public Date getFdGraduationDate() {
		return this.fdGraduationDate;
	}

	/**
	 * 毕业日期
	 */
	public void setFdGraduationDate(Date fdGraduationDate) {
		this.fdGraduationDate = fdGraduationDate;
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
		return docMain;
	}

	public void setDocMain(HrStaffEntry docMain) {
		this.docMain = docMain;
	}

	public Integer getDocIndex() {
		return docIndex;
	}

	public void setDocIndex(Integer docIndex) {
		this.docIndex = docIndex;
	}

	public HrStaffPersonInfoSettingNew getFdEducation() {
		return fdEducation;
	}

	public void setFdEducation(HrStaffPersonInfoSettingNew fdEducation) {
		this.fdEducation = fdEducation;
	}
	
}
