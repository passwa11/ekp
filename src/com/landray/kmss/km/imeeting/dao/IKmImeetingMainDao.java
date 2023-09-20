package com.landray.kmss.km.imeeting.dao;

import com.landray.kmss.common.dao.IBaseDao;

/**
 * 会议安排数据访问接口
 */
public interface IKmImeetingMainDao extends IBaseDao {

	public int updateDocumentTemplate(String ids, String templateId)
			throws Exception;

}
