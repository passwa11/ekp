package com.landray.kmss.sys.attend.service.spring;

import java.util.Map;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attend.dao.ISysAttendLactationDetailDao;
import com.landray.kmss.sys.attend.dao.SQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendLactationDetail;
import com.landray.kmss.sys.attend.service.ISysAttendLactationDetailService;
import com.sunbor.web.tag.Page;

/**
 * 哺乳产前实现
 * 
 * @author
 * @version 1.0 2017-05-24
 */
/**
 * @author linxiuxian
 *
 */
public class SysAttendLactationDetailServiceImp extends BaseServiceImp implements ISysAttendLactationDetailService {
	@Override
	public Page findPage(SQLInfo sqlInfo) throws Exception {
		return ((ISysAttendLactationDetailDao) getBaseDao()).findPage(sqlInfo);
	}

	@Override
	public SysAttendLactationDetail findByPrimaryKey(String id, Map<String, Object> params) throws Exception {
		return ((ISysAttendLactationDetailDao) getBaseDao()).findByPrimaryKey(id, params);
	}

}
