package com.landray.kmss.km.calendar.cms.interfaces;

import java.util.Date;
import java.util.List;

public interface ICMSProvider {

	/**
	 * 获取provider实例
	 * 
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public ICMSProvider getNewInstance(String personId) throws Exception;

	/**
	 * 添加记录
	 * 
	 * @param personId
	 *            用户id
	 * @param syncroCommonCal
	 * @return 新添加记录的UUID
	 * @throws Exception
	 */
	public String addCalElement(String personId, SyncroCommonCal syncroCommonCal)
			throws Exception;

	/**
	 * 更新记录
	 * 
	 * @param personId
	 * @param syncroCommonCal
	 * @return 操作是否成功
	 */
	public boolean updateCalElement(String personId,
			SyncroCommonCal syncroCommonCal) throws Exception;

	/**
	 * 删除记录
	 * 
	 * @param personId
	 * @param uuid
	 *            当接口在立即同步的情况下调用时接出表没有数据，此时uuId取ekpId，第三方模块拿到ekpId自行处理
	 *            当接口在批量同步的情况下调用时接触有数据，此时uuId为与第三方规定好的uuId
	 * @return 操作是否成功
	 * @throws Exception
	 */
	public boolean deleteCalElement(String personId, String uuid)
			throws Exception;

	/**
	 * 获取最近更新的所有记录，包括新增、更新、删除
	 * 
	 * @param personId
	 * @param date
	 * @return
	 */
	public List<SyncroCommonCal> getCalElements(String personId, Date date)
			throws Exception;

	/**
	 * 获取最近新增的记录
	 * 
	 * @param personId
	 * @param date
	 * @return
	 */
	public List<SyncroCommonCal> getAddedCalElements(String personId, Date date)
			throws Exception;

	/**
	 * 获取最近删除的记录
	 * 
	 * @param personId
	 * @param date
	 * @return
	 */
	public List<SyncroCommonCal> getDeletedCalElements(String personId,
			Date date) throws Exception;

	/**
	 * 获取最近更新的记录
	 * 
	 * @param personId
	 * @param date
	 * @return
	 */
	public List<SyncroCommonCal> getUpdatedCalElements(String personId,
			Date date) throws Exception;

	/**
	 * 该provider同步时对应的日程类型，现有的类型为：日程、笔记
	 * 
	 * @return
	 */
	public String getCalType();

	/**
	 * 获取所有需要同步的用户ID，按用户ID升序排列
	 * 
	 * @param personId
	 * @param date
	 * @return
	 */
	public List<String> getPersonIdsToSyncro();

	/**
	 * 该用户的日程数据是否需要跟当前应用进行同步
	 * 
	 * @param personId
	 * @return
	 */
	public boolean isNeedSyncro(String personId);
	
	public boolean isSynchroEnable() throws Exception;

}
