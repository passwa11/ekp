package com.landray.kmss.hr.staff.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;

/**
 * 员工信息日志
 * 
 * @author 潘永辉 2017-1-7
 * 
 */
public interface IHrStaffPersonInfoLogService extends IBaseService {

	/**
	 * 通过操作方法和操作记录保存一条日志
	 * 
	 * @param fdParaMethod
	 * @param fdDetails
	 * @return
	 */
	public HrStaffPersonInfoLog buildPersonInfoLog(String fdParaMethod,
			String fdDetails) throws Exception;

	public void savePersonInfoLog(String fdParaMethod, String fdDetails)
			throws Exception;
}
