package com.landray.kmss.sys.attend.service;

import java.util.Date;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.model.SysAttendStatMonth;

import net.sf.json.JSONObject;
/**
 * 月统计表业务对象接口
 * 
 * @author 
 * @version 1.0 2017-08-17
 */
public interface ISysAttendStatMonthService extends IBaseService {

	/**
	 * 统计考勤月记录数
	 * 
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public JSONObject sumAttendCount(Date dateTime, String fdDeptId)
			throws Exception;

	/**
	 * 统计某部门的考勤数据
	 * 
	 * @param dateTime
	 * @param leaderId
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	public JSONObject statLeaderMonth(Date dateTime, String leaderId,
			String deptId)
			throws Exception;
	
	public SysAttendStatMonth getDataBypersonMonth(Date fdMonth,String docCreatorId)  throws Exception;

}
