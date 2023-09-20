package com.landray.kmss.sys.attend.dao;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.attend.model.SysAttendMain;

public interface ISysAttendSynDingDao extends IBaseDao {
	/**
	 * 增加原始记录
	 * 
	 * @param modelObj
	 *            model对象
	 * @throws Exception
	 */
	public abstract String addRecord(SysAttendMain main) throws Exception;

}
