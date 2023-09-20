package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

import java.util.Date;
import java.util.List;

/**
 * 月统计
 * 
 * @author linxiuxian
 *
 */
public interface ISysAttendStatMonthJobService {

	/**
	 * 系统定时任务
	 * 
	 * @param jobContext
	 * @throws Exception
	 */
	public void execute(SysQuartzJobContext jobContext) throws Exception;

	/**
	 * 统计所有考勤组某个月
	 * @param date
	 * @throws Exception
	 */
	public void stat(Date date) throws Exception;

	/**
	 * 统计某个人某个月
	 * 
	 * @param orgElement
	 * @param date
	 * @throws Exception
	 */
	public void stat(SysOrgElement orgElement, Date date) throws Exception;

	/**
	 * 统计某些用户某些月份数据
	 * 
	 * @param eleList
	 * @param monthList
	 * @throws Exception
	 */
	public void stat(List<SysOrgElement> eleList, List<Date> monthList)
			throws Exception;

	/**
	 * 统计某个考勤组，某月的数据
	 * 
	 * @param fdCategoryId
	 * @param date
	 * @throws Exception
	 */
	public void stat(String fdCategoryId, Date date)throws Exception;
	/**
	 * 统计指定人员列表，某月的数据
	 * @param date
	 * @param orgList
	 *            用户id列表(fdCategoryId与orgList至少一项不允许为空)
	 * @throws Exception
	 */
	public void stat(String fdCategoryId,Date date, List<String> orgList) throws Exception;

	/**
	 * 统计某个人某个时间段（从某天到某天）
	 * 
	 * @param orgElement
	 * @param beginTime
	 * @param endTime
	 * @throws Exception
	 */
	public void stat(SysOrgElement orgElement, Date beginTime, Date endTime)
			throws Exception;

	/**
	 * 统计某个考勤组某个时间段（从某天到某天）
	 * 
	 * @param fdCategoryId
	 * @param beginTime
	 * @param endTime
	 * @throws Exception
	 */
	public void stat(String fdCategoryId, Date beginTime, Date endTime)
			throws Exception;
	
	/**
	 * 根据考勤组,日期删除月统计数据
	 * 
	 * @param fdCategoryId
	 * @param date
	 * @throws Exception
	 */
	public void deletStat(String fdCategoryId, Date date) throws Exception;

	/**
	 * 根据考勤组,日期删除月统计数据
	 * 
	 * @param fdCategoryId
	 * @param date
	 * @param orgList
	 *            用户id列表(fdCategoryId与orgList至少一项不允许为空)
	 * @throws Exception
	 */
	public void deletStat(String fdCategoryId, Date date, List<String> orgList)
			throws Exception;
	
	/**
	 * 统计某些人某个月
	 * @param eleList
	 * @param monthList
	 * @throws Exception
	 */
	public void statMonth(List<String> eleList, List<Date> monthList) throws Exception;
}
