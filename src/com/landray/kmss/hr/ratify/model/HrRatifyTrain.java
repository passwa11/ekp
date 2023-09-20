package com.landray.kmss.hr.ratify.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.hr.ratify.forms.HrRatifyTrainForm;
import com.landray.kmss.util.DateUtil;

/**
 * 培训记录
 */
public class HrRatifyTrain extends BaseModel {

	private static ModelToFormPropertyMap toFormPropertyMap;

	private String fdName;

	private Date fdStartDate;

	private Date fdEndDate;

	private String fdTrainCompany;

	private String fdRemark;

	private HrRatifyEntry docMain;

	private Integer docIndex;

	@Override
	public Class<HrRatifyTrainForm> getFormClass() {
		return HrRatifyTrainForm.class;
	}

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdStartDate",
					new ModelConvertor_Common("fdStartDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEndDate",
					new ModelConvertor_Common("fdEndDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("docMain.fdName", "docMainName");
			toFormPropertyMap.put("docMain.fdId", "docMainId");
		}
		return toFormPropertyMap;
	}

	/**
	 * 名称
	 */
	public String getFdName() {
		return this.fdName;
	}

	/**
	 * 名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 开始时间
	 */
	public Date getFdStartDate() {
		return this.fdStartDate;
	}

	/**
	 * 开始时间
	 */
	public void setFdStartDate(Date fdStartDate) {
		this.fdStartDate = fdStartDate;
	}

	/**
	 * 结束时间
	 */
	public Date getFdEndDate() {
		return this.fdEndDate;
	}

	/**
	 * 结束时间
	 */
	public void setFdEndDate(Date fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	/**
	 * 培训单位
	 */
	public String getFdTrainCompany() {
		return this.fdTrainCompany;
	}

	/**
	 * 培训单位
	 */
	public void setFdTrainCompany(String fdTrainCompany) {
		this.fdTrainCompany = fdTrainCompany;
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
		return docMain;
	}

	public void setDocMain(HrRatifyEntry docMain) {
		this.docMain = docMain;
	}

	public Integer getDocIndex() {
		return docIndex;
	}

	public void setDocIndex(Integer docIndex) {
		this.docIndex = docIndex;
	}

}
