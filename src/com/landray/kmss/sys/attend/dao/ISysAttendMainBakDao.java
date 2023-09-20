package com.landray.kmss.sys.attend.dao;

import java.util.Map;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.attend.model.SysAttendMainBak;
import com.sunbor.web.tag.Page;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-01
 */
public interface ISysAttendMainBakDao extends IBaseDao {

	public Page findPage(SQLInfo sqlInfo) throws Exception;

	public SysAttendMainBak findByPrimaryKey(String id,
			Map<String, Object> params) throws Exception;

}
