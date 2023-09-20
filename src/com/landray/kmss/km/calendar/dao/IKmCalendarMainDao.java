package com.landray.kmss.km.calendar.dao;

import java.util.List;

import com.landray.kmss.common.dao.IBaseDao;

/**
 * 日程管理主文档数据访问接口
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public interface IKmCalendarMainDao extends IBaseDao {

	public void clearCalendarLabel(String labelId) throws Exception;

	public void batchClearCalendarLabel(List<String> labelIds) throws Exception;

}
