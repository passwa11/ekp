package com.landray.kmss.sys.attend.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * 日统计
 * 
 * @author linxiuxian
 *
 */
public interface ISysAttendStatJobService {

	/**
	 * 系统当天实时定时任务
	 *
	 * @param jobContext
	 * @throws Exception
	 */
	public void execute(SysQuartzJobContext jobContext) throws Exception;

	/**
	 * 根据日期统计
	 * 
	 * @param date
	 * @throws Exception
	 */
	public void stat(Date date) throws Exception;

	/**
	 * 重新统计考勤数据(日/月)
	 * 
	 * @param orgElement
	 * @param date
	 * @throws Exception
	 */
	public void stat(SysOrgElement orgElement, Date date,Map<String, JSONObject> monthDataMap) throws Exception;

	/**
	 * 重新统计考勤数据
	 * 
	 * @param orgElement
	 * @param date
	 * @param isStatMonth
	 *            是否需要重新统计月度数据
	 * @throws Exception
	 */
	public void stat(SysOrgElement orgElement, Date date, Boolean isStatMonth,Map<String, JSONObject> monthDataMap)
			throws Exception;

	/**
	 * 重新统计考勤数据
	 * 
	 * @param orgIdList
	 *            用户Id列表
	 * @param dateList
	 *            日期列表
	 * @throws Exception
	 */
	public void stat(List<String> orgIdList, List<Date> dateList)
			throws Exception;

	/**
	 * 统计某个给定用户，某天的数据
	 * @param date
	 * @param isStatMonth
	 *            是否需要重新统计月度数据
	 * @param orgList
	 * @throws Exception
	 */
	public void stat( Date date, Boolean isStatMonth, List<String> orgList,Map<String, JSONObject> monthDataMap) throws Exception;
	/**
	 * 删除某个考勤组或某些用户，某天的统计数据
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
	 * 重新统计考勤信息
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void restat(SysQuartzJobContext context) throws Exception;

}
