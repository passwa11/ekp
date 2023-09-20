package com.landray.kmss.hr.staff.webservice;

import javax.jws.WebService;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;
@WebService
public interface IHrStaffWebService extends ISysWebservice{
	/**
	 * 获取人员信息
	 * 
	 * @param orgContext
	 * @return
	 * @throws Exception
	 */
	public HrStaffOrgResult getHrStaffElements(HrStaffGetOrgContext hrStaffGetOrgContext
			) throws Exception;
}
