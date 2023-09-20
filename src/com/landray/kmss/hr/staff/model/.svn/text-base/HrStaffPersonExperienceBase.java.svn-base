package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.util.DateUtil;

/**
 * 个人经历基类
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public abstract class HrStaffPersonExperienceBase extends HrStaffBaseModel {
	private static final long serialVersionUID = 1L;
	// 相关流程
	private String fdRelatedProcess;
	// 开始日期
	private Date fdBeginDate;
	// 结束日期
	private Date fdEndDate;
	// 备注
	private String fdMemo;

	private Date fdContractPeriod;

	private String fdContractUnit;

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			// 由于界面没有datetime选择，只使用date
			toFormPropertyMap.put("fdContractPeriod", new ModelConvertor_Common(
					"fdContractPeriod").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdBeginDate", new ModelConvertor_Common(
					"fdBeginDate").setDateTimeType(DateUtil.TYPE_DATE));
			toFormPropertyMap.put("fdEndDate", new ModelConvertor_Common(
					"fdEndDate").setDateTimeType(DateUtil.TYPE_DATE));
		}
		return toFormPropertyMap;
	}

	public Date getFdContractPeriod() {
		return fdContractPeriod;
	}

	public void setFdContractPeriod(Date fdContractPeriod) {
		this.fdContractPeriod = fdContractPeriod;
	}

	public String getFdContractUnit() {
		return fdContractUnit;
	}

	public void setFdContractUnit(String fdContractUnit) {
		this.fdContractUnit = fdContractUnit;
	}

	public Date getFdBeginDate() {
		return fdBeginDate;
	}

	public void setFdBeginDate(Date fdBeginDate) {
		this.fdBeginDate = fdBeginDate;
	}

	public Date getFdEndDate() {
		return fdEndDate;
	}

	public void setFdEndDate(Date fdEndDate) {
		this.fdEndDate = fdEndDate;
	}

	public String getFdMemo() {
		return fdMemo;
	}

	public void setFdMemo(String fdMemo) {
		this.fdMemo = fdMemo;
	}
	public String getFdRelatedProcess() {
		return fdRelatedProcess;
	}

	public void setFdRelatedProcess(String fdRelatedProcess) {
		this.fdRelatedProcess = fdRelatedProcess;
	}
}
