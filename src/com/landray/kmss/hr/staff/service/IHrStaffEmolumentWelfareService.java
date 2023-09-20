package com.landray.kmss.hr.staff.service;

import java.util.List;

import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfare;

/**
 * 薪酬福利
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public interface IHrStaffEmolumentWelfareService extends IHrStaffImportService {
	public List<HrStaffEmolumentWelfare>
			getEmolumentWelfareByPerson(String personInfoId) throws Exception;

}
