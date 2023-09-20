package com.landray.kmss.sys.filestore.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.filestore.dao.ISysFileConvertQueueDao;
import org.hibernate.query.NativeQuery;

public class SysFileConvertQueueDaoImp extends BaseDaoImp implements ISysFileConvertQueueDao {

	@Override
	public void clearInvalid() {
		try {
			NativeQuery sqlQuery = getHibernateSession().createNativeQuery(
					"delete from sys_file_convert_queue where ((fd_file_id is not null or fd_file_id <> '') and fd_file_id not in (select fd_id from sys_att_file)) or fd_attmain_id not in (select fd_id from sys_att_main union all select fd_id from sys_att_rtf_data)");
			sqlQuery.addSynchronizedQuerySpace("sys_file_convert_queue").executeUpdate();
		} catch (Exception e) {
			//
		}
	}

}
