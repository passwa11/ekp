package com.landray.kmss.sys.attend.service.spring;

import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.attend.dao.ISysAttendSynDingBakDao;
import com.landray.kmss.sys.attend.dao.SQLInfo;
import com.landray.kmss.sys.attend.model.SysAttendSynDingBak;
import com.landray.kmss.sys.attend.service.ISysAttendSynDingBakService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.sunbor.web.tag.Page;

public class SysAttendSynDingBakServiceImp extends ExtendDataServiceImp
		implements ISysAttendSynDingBakService {

	@Override
	public List<String> getRemainYears() throws Exception {
		String recordSql = "select distinct(fd_year) from sys_attend_syn_ding_baklog order by fd_year";
		List<String> recordList = this.getBaseDao().getHibernateSession().createNativeQuery(recordSql).list();
		return recordList;
	}

	@Override
	public Page findPage(SQLInfo sqlInfo) throws Exception {
		return ((ISysAttendSynDingBakDao) getBaseDao()).findPage(sqlInfo);
	}

	@Override
	public SysAttendSynDingBak findByPrimaryKey(String id,
												Map<String, Object> params) throws Exception {
		return ((ISysAttendSynDingBakDao) getBaseDao()).findByPrimaryKey(id,
				params);
	}

	@Override
	public boolean isExist(SQLInfo sqlInfo) throws Exception {
		return ((ISysAttendSynDingBakDao) getBaseDao()).isExist(sqlInfo);
	}
}
