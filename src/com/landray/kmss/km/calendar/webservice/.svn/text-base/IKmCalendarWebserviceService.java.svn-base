package com.landray.kmss.km.calendar.webservice;

import com.landray.kmss.sys.webservice2.interfaces.ISysWebservice;

import javax.jws.WebService;

/**
 * 日程管理WebService2接口
 */
@WebService
public interface IKmCalendarWebserviceService extends ISysWebservice {

	/**
	 * 创建日程/笔记
	 * 
	 * @param kmCalendarParamterForm
	 *            日程参数Form
	 * @return KmCalendarResult 操作返回值
	 */
	public KmCalendarResult addCalendar(
			KmCalendarParamterForm kmCalendarParamterForm) throws Exception;

	/**
	 * 变更日程/笔记
	 * 
	 * @param kmCalendarParamterForm
	 *            日程参数Form
	 * @return KmCalendarResult 操作返回值
	 */
	public KmCalendarResult updateCalendar(
			KmCalendarParamterForm kmCalendarParamterForm) throws Exception;

	/**
	 * 读取日程/笔记信息
	 * 
	 * @param fdAppUUId
	 *            外部系统id,不存在则与EKP中的fdId一致
	 * @param appKey
	 *            标识日程或笔记来源的系统
	 */
	public KmCalendarParamterForm viewCalendar(String fdAppUUId, String appKey)
			throws Exception;

	/**
	 * 删除日程/笔记信息
	 * 
	 * @param fdAppUUId
	 *            外部系统id,不存在则与EKP中的fdId一致
	 * @param appKey
	 *            标识日程或笔记来源的系统
	 * @param docCreator
	 *            操作人 与kmCalendarParamterForm中的docCreator格式一致
	 */
	public KmCalendarResult deleteCalendar(String fdAppUUId, String appKey, String docCreator)
			throws Exception;

	/**
	 * 查询日程/笔记
	 * 
	 * @param kmCalendarQueryContext
	 *            日程查询上下文
	 * @return KmCalendarResult 操作返回值
	 */
	public KmCalendarResult listCalendar(
			KmCalendarWsQueryContext kmCalendarWsQueryContext) throws Exception;

	/**
	 * 指定人员指定时间冲突检测
	 * 
	 * @param kmCalendarCheckContext
	 *            冲突检测上下文
	 * @return KmCalendarResult 操作返回值
	 */
	public KmCalendarResult conflictCheck(
			KmCalendarCheckContext kmCalendarCheckContext) throws Exception;

	/**
	 * 日程提醒设置
	 * 
	 * @param fdAppUUId
	 *            外部系统id,不存在则与EKP中的fdId一致
	 * @param kmCalendarNotify
	 *            提醒列表
	 */
	public KmCalendarResult setNotify(String fdAppUUId, String appKey,
			String kmCalendarNotify) throws Exception;

}
