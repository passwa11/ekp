package com.landray.kmss.sys.time.interfaces;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.time.model.SysTimeArea;
import net.sf.json.JSONArray;

import java.util.Date;
import java.util.List;

/**
 * 计算工时和自然周期接口
 * 
 * 创建日期 2008-一月-15
 * 
 * @author 易荣烽
 */

public interface ISysTimeCountService {

	/**
	 * 根据开始时间和结束时间计算自然周期
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 一串數字
	 * @throws Exception
	 */
	public abstract long getPeriod(long startTime, long endTime)
			throws Exception;

	/**
	 * 根据组织架构id查找所属区域组，根据开始时间和结束时间匹配班次等来计算工时
	 * 
	 * @param id
	 *            组织架构id
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 一串數字
	 * @throws Exception
	 */
	public abstract long getManHour(String id, long startTime, long endTime)
			throws Exception;

	/**
	 * 根据组织架构对象查找所属区域组，根据开始时间和结束时间匹配班次等来计算工时
	 * 
	 * @param element
	 *            组织架构元素
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 一串數字
	 * @throws Exception
	 */
	public abstract long getManHour(SysOrgElement element, long startTime,
			long endTime) throws Exception;

	/**
	 * 根据组织架构id查找所属区域组，以及指定的起始时间和指定时间间隔类型，返回指定的时间间隔工时的日期
	 * 
	 * @param id
	 *            组织架构id
	 * @param startTime
	 *            开始时间
	 * @param numberOfDate
	 *            时间间隔
	 * @param field
	 *            间隔类型
	 * @return
	 * @throws Exception
	 */
	Date getEndTimeForWorkingHours(String id, Date startTime, int numberOfDate,
			HoursField field) throws Exception;

	/**
	 * 根据组织架构对象查找所属区域组，以及指定的起始时间和指定时间间隔类型，返回指定的时间间隔工时的日期
	 * 
	 * @param element
	 *            组织架构元素
	 * @param startTime
	 *            开始时间
	 * @param numberOfDate
	 *            时间间隔
	 * @param field
	 *            间隔类型
	 * @return
	 * @throws Exception
	 */
	Date getEndTimeForWorkingHours(SysOrgElement element, Date startTime,
			int numberOfDate, HoursField field) throws Exception;

	/**
	 * 根据组织架构id查找所属区域组，根据开始时间和结束时间匹配班次等来计算工作日天数
	 * 
	 * @param id
	 *            组织架构id
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 工作日天数
	 * @throws Exception
	 */
	public abstract int getWorkingDays(String id, long startTime, long endTime)
			throws Exception;

	/**
	 * 根据组织架构对象查找所属区域组，根据开始时间和结束时间匹配班次等来计算工作日天数
	 * 
	 * @param element
	 *            组织架构元素
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return 工作日天数
	 * @throws Exception
	 */
	public abstract int getWorkingDays(SysOrgElement element, long startTime,
			long endTime) throws Exception;

	/**
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public SysTimeArea getTimeArea(SysOrgElement element) throws Exception;

	/**
	 * @param id
	 *            组织架构id
	 * @param time
	 *            时间
	 * @return 上班，假期，假期补班，节假日，节假日补班
	 * @throws Exception
	 */
	public abstract List<String> getWorkState(String id, Date time)
			throws Exception;

	/**
	 * @param id
	 * @return 返回当前用户所有的节假日和补班数据
	 * @throws Exception
	 */
	public abstract JSONArray getHolidayPachDay(String id) throws Exception;

	/**
	 * @param element
	 * @param date
	 * @return
	 * @throws Exception
	 *             获取用户某天的工作班次
	 */
	public abstract com.alibaba.fastjson.JSONObject getWorkTimes(SysOrgElement element, Date date)
			throws Exception;

	/**
	 * @param element
	 * @param fdLeaveTime
	 * @return
	 * @throws Exception
	 */
	public abstract double getUserLeaveAmount(SysOrgElement element,
			String fdLeaveTime) throws Exception;
}