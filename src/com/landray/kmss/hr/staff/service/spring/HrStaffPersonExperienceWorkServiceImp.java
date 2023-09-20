package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceWorkService;

/**
 * 工作经历
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceWorkServiceImp extends
		HrStaffPersonExperienceBaseServiceImp implements
		IHrStaffPersonExperienceWorkService {

	@Override
	public String[] getImportFields() {
		// 公司、职位、开始时间、结束时间、工作描述、离开原因
		return new String[] { "fdCompany", "fdPosition", "fdBeginDate",
				"fdEndDate", "fdDescription", "fdReasons" };
	}

	@Override
	public String getTypeString() {
		return "工作经历";
	}

}
