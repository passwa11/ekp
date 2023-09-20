package com.landray.kmss.hr.staff.service;

import java.util.List;

import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBase;

/**
 * 个人经历基类
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public interface IHrStaffPersonExperienceBaseService extends
		IHrStaffImportService {

	/**
	 * 根据人员信息ID获取经历集合
	 * 
	 * @param personInfoId
	 * @return
	 * @throws Exception
	 */
	public List<HrStaffPersonExperienceBase> getHrStaffPersonExperiences(
			String personInfoId) throws Exception;
	
}
