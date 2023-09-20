package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.attend.service.ISysAttendMainLogJobService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;

import java.util.Date;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-01-21
 */
public class SysAttendMainLogJobServiceImp
		implements ISysAttendMainLogJobService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendMainLogJobServiceImp.class);

	@Override
	public void execute(SysQuartzJobContext jobContext) throws Exception {
		IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
		try {
			NativeQuery nativeQuery =baseDao.getHibernateSession().createNativeQuery(
					"delete from sys_attend_main_log where doc_create_time<:endTime or fd_create_time<:endTime");

			nativeQuery.setTimestamp("endTime",
							AttendUtil.getDate(new Date(), -90));
			nativeQuery.addSynchronizedQuerySpace("sys_attend_main_log");
			nativeQuery.executeUpdate();
		} catch (Exception e) {
			logger.error("清空打卡日志失败", e);
			e.printStackTrace();
			throw e;
		}
	}
}
