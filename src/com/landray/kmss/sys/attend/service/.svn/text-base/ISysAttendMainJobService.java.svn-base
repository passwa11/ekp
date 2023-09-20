package com.landray.kmss.sys.attend.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;

/**
 * @author linxiuxian
 *
 */
public interface ISysAttendMainJobService {

	/**
	 * 非跨天打卡的考勤组，缺卡补卡并统计，执行时间每天2点
	 * 
	 * @param jobContext
	 * @throws Exception
	 */
	public void execute(SysQuartzJobContext jobContext) throws Exception;


	/**
	 * 跨天考勤组的缺卡，每25分钟检查考勤组的结束时间
	 * 
	 * @param jobContext
	 * @throws Exception
	 */
	public void executeAnother(SysQuartzJobContext jobContext) throws Exception;

	/**
	 * 根据考勤组、日期缺卡补卡并统计
	 * 
	 * @param date
	 *            不允许为空
	 * @param categoryId
	 *            考勤组ID
	 * @param isCalMissed
	 *            是否写入缺卡记录
	 * @param isStatMonth
	 *            是否需要重新统计月度数据
	 * @param orgList
	 *            用户id列表(categoryId与orgList至少一项不允许为空)
	 * @throws Exception
	 */
	public void stat(Date date, String categoryId, boolean isCalMissed,
			Boolean isStatMonth, List<String> orgList,Map<String, JSONObject> monthDataMap) throws Exception;

	/**
	 * 统计昨天的数据	
	 * 
	 * @throws Exception
	 */
	public void statYesterday(SysQuartzJobContext jobContext) throws Exception;

}
