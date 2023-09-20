package com.landray.kmss.hr.staff.service;

import java.util.Date;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;

/**
 * 薪酬福利明细
 * 
 * @author 潘永辉 2017-1-14
 * 
 */
public interface IHrStaffEmolumentWelfareDetaliedService extends IBaseService {

	/**
	 * <p>获取当前员工薪资</p>
	 * @param fdId
	 * @author sunj
	 */
	public Double getSalaryByStaffId(HrStaffPersonInfo fdPersonInfo) throws Exception;

	public void updateSalary(String fdId, Double fdTransSalary, Date fdEffectTime, String fdOrgPersonId) throws Exception;

	public void setSalarySchedulerJob(Date fdTransDate, HrStaffPersonInfo fdPersonInfo, String fdId) throws Exception;

}
