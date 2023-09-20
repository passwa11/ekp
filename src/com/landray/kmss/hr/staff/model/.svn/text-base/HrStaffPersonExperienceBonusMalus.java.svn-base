package com.landray.kmss.hr.staff.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceBonusMalusForm;
import com.landray.kmss.util.DateUtil;

/**
 * 奖惩信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceBonusMalus extends
		HrStaffPersonExperienceBase {
	private static final long serialVersionUID = 1L;

	// 奖惩名称
	private String fdBonusMalusName;
	// 奖惩日期
	private Date fdBonusMalusDate;

	@Override
	public Class getFormClass() {
		return HrStaffPersonExperienceBonusMalusForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());

			// 由于界面没有datetime选择，只使用date
			toFormPropertyMap.put("fdBonusMalusDate",
					new ModelConvertor_Common("fdBonusMalusDate")
							.setDateTimeType(DateUtil.TYPE_DATE));
		}
		return toFormPropertyMap;
	}

	public String getFdBonusMalusName() {
		return fdBonusMalusName;
	}

	public void setFdBonusMalusName(String fdBonusMalusName) {
		this.fdBonusMalusName = fdBonusMalusName;
	}

	public Date getFdBonusMalusDate() {
		return fdBonusMalusDate;
	}

	public void setFdBonusMalusDate(Date fdBonusMalusDate) {
		this.fdBonusMalusDate = fdBonusMalusDate;
	}

	// 奖惩类型
	private String fdBonusMalusType;

	public String getFdBonusMalusType() {
		return fdBonusMalusType;
	}

	public void setFdBonusMalusType(String fdBonusMalusType) {
		this.fdBonusMalusType = fdBonusMalusType;
	}

}
