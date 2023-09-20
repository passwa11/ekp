package com.landray.kmss.sys.filestore.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.filestore.dao.ISysFileConvertLogDao;
import com.landray.kmss.sys.filestore.service.ISysFileConvertLogService;

public class SysFileConvertLogServiceImp extends BaseServiceImp implements ISysFileConvertLogService {

	@Override
	public void deleteInvalidLogs() {
		ISysFileConvertLogDao logDao = (ISysFileConvertLogDao) getBaseDao();
		logDao.deleteInvalidLogs();
	}

	@Override
	public void deleteLogs(String delType, String queueId, String[] ids) throws Exception {
		if ("all".equals(delType)) {
			ISysFileConvertLogDao logDao = (ISysFileConvertLogDao) getBaseDao();
			logDao.deleteLogs(queueId);
		} else {
			delete(ids);
		}
	}

}
