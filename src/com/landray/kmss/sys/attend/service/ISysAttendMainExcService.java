package com.landray.kmss.sys.attend.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.service.IBaseService;
/**
 * 签到异常表业务对象接口
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public interface ISysAttendMainExcService extends IBaseService {

	/**
	 * @param ids
	 * @param fdStatus
	 * @throws Exception
	 */
	public void updateStatus(String[] ids, int fdStatus) throws Exception;

	/**
	 * 特权通过异常单流程
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void passByAdmin(String id) throws Exception;
	
	
	/**
	 * 获取截止日期忘打卡的次数
	 * @param personIds
	 * @param fdDesc
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getNumByPerson(List<String> personIds,Date fdEndDate) throws Exception;

}
