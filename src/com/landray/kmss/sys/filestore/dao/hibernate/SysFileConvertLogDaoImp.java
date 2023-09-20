package com.landray.kmss.sys.filestore.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.filestore.dao.ISysFileConvertLogDao;

public class SysFileConvertLogDaoImp extends BaseDaoImp implements ISysFileConvertLogDao {

	@Override
	public void deleteInvalidLogs() {
		getHibernateSession().createNativeQuery(
						"delete from sys_file_convert_log where fd_queue_id in (select fd_id from sys_file_convert_queue where fd_file_id not in (select fd_id from sys_att_file) union all select fd_id from sys_file_convert_queue where fd_attmain_id not in (select fd_id from sys_att_main union all select fd_id from sys_att_rtf_data))")
				.addSynchronizedQuerySpace("sys_file_convert_log").executeUpdate();
	}

	@Override
	public void deleteLogs(String queueId) {
		getHibernateSession().createNativeQuery("delete from sys_file_convert_log where fd_queue_id ='" + queueId + "'")
				.addSynchronizedQuerySpace("sys_file_convert_log").executeUpdate();
	}

}
