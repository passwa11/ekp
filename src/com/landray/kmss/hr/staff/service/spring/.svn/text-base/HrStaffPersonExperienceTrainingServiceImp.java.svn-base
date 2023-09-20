package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceTrainingService;

/**
 * 培训记录
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceTrainingServiceImp extends
		HrStaffPersonExperienceBaseServiceImp implements
		IHrStaffPersonExperienceTrainingService {

	@Override
	public String[] getImportFields() {
		// 培训名称、开始日期、结束日期、培训单位、备注
		return new String[] { "fdTrainingName", "fdBeginDate", "fdEndDate",
				"fdTrainingUnit", "fdMemo" };
	}

	@Override
	public String getTypeString() {
		return "培训记录";
	}

}
