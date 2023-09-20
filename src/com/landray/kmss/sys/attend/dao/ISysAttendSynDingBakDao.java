package com.landray.kmss.sys.attend.dao;

import java.util.Map;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.attend.model.SysAttendSynDingBak;
import com.sunbor.web.tag.Page;

public interface ISysAttendSynDingBakDao extends IBaseDao {
	public Page findPage(SQLInfo sqlInfo) throws Exception;

	public SysAttendSynDingBak findByPrimaryKey(String id,
			Map<String, Object> params) throws Exception;

	public boolean isExist(SQLInfo sqlInfo) throws Exception;
}
