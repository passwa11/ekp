package com.landray.kmss.third.im.kk.dao;

import com.landray.kmss.common.dao.IBaseDao;

/**
 * kk待办集成日志数据访问接口
 * 
 * @author
 * @version 1.0 2012-04-13
 */
public interface IKkNotifyLogDao extends IBaseDao {

	public void backUp() throws Exception;

	public void clean(String logDays) throws Exception;

}
