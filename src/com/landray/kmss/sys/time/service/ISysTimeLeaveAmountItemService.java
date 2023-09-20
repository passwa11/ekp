package com.landray.kmss.sys.time.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-13
 */
public interface ISysTimeLeaveAmountItemService extends IBaseService {
	/**
	 * 获取人员的假期类型对应的明细
	 * @param personId 人员id
	 * @param year 年份
	 * @param leaveType 假期类型CODE
	 * @return
	 * @throws Exception
	 */
	public SysTimeLeaveAmountItem getAmountItem(String personId, Integer year,
												String leaveType) throws Exception;
	/**
	 * 废弃
	 * 
	 * @param leaveName
	 * @throws Exception
	 */
	public void deleteByName(String leaveName) throws Exception;

	/**
	 * @param leaveType
	 * @throws Exception
	 */
	public void deleteByLeaveType(String leaveType) throws Exception;

}
