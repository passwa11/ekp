package com.landray.kmss.sys.time.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
/**
 * 节假日设置业务对象接口
 * 
 * @author 
 * @version 1.0 2017-09-26
 */
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.model.SysTimeHolidayPach;
public interface ISysTimeHolidayService extends IBaseService {
	
	/**
	 * @param fdHolidayId
	 * @return
	 * @throws Exception
	 * 根据节假日Id获取所有的节假日明细
	 */
	public List<SysTimeHolidayDetail> getHolidayDetail(String fdHolidayId)
			throws Exception;
	
	/**
	 * @param fdHolidayDetailId
	 * @return
	 * @throws Exception
	 * 根据节假日明细Id获取对应的补班日期(如果国庆节的补班日期)
	 */
	public List<SysTimeHolidayPach> getHolidayPach(String fdHolidayDetailId)
			throws Exception;
	
	/**
	 * @param fdHolidayId
	 * @return
	 * @throws Exception
	 * 根据节假日Id获取所有的补班日期
	 */
	public List<SysTimeHolidayPach> getHolidayPachs(String fdHolidayId)
			throws Exception;
}
