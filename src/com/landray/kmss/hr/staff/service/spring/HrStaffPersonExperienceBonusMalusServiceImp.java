package com.landray.kmss.hr.staff.service.spring;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBonusMalusService;

/**
 * 奖惩信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceBonusMalusServiceImp extends
		HrStaffPersonExperienceBaseServiceImp implements
		IHrStaffPersonExperienceBonusMalusService {

	@Override
	public String[] getImportFields() {
		// 奖惩名称、奖惩类型，奖惩日期、备注
		return new String[] { "fdBonusMalusName", "fdBonusMalusType","fdBonusMalusDate", "fdMemo" };
	}

	@Override
	public String getTypeString() {
		return "奖惩信息";
	}

	@Override
	public List<String> getCheckFields() {
		//奖惩类型
		List<String> checkFileds = new ArrayList<String>();
		checkFileds.add("fdBonusMalusType"); 
		return checkFileds;
	}
}
