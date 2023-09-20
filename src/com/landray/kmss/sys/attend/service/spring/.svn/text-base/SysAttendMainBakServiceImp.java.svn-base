package com.landray.kmss.sys.attend.service.spring;

import java.util.Map;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.dao.ISysAttendMainBakDao;
import com.landray.kmss.sys.attend.dao.SQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendMainBak;
import com.landray.kmss.sys.attend.service.ISysAttendMainBakService;
import com.sunbor.web.tag.Page;

/**
 *
 * @author cuiwj
 * @version 1.0 2018-12-01
 */
public class SysAttendMainBakServiceImp extends BaseServiceImp
		implements ISysAttendMainBakService {

	@Override
	public Page findPage(SQLInfo sqlInfo) throws Exception {
		return ((ISysAttendMainBakDao) getBaseDao()).findPage(sqlInfo);
	}

	@Override
	public SysAttendMainBak findByPrimaryKey(String id,
											 Map<String, Object> params) throws Exception {
		return ((ISysAttendMainBakDao) getBaseDao()).findByPrimaryKey(id,
				params);
	}

}
