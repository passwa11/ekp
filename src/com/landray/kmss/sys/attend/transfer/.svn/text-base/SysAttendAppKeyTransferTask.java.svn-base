package com.landray.kmss.sys.attend.transfer;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.util.AttendPlugin;
import com.landray.kmss.util.SpringBeanUtil;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议签到增加fdAppKey字段值填充数据迁移
 * 
 */
public class SysAttendAppKeyTransferTask
		implements ISysAdminTransferChecker, ISysAdminTransferTask {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendAppKeyTransferTask.class);

	@Override
	public SysAdminTransferResult
			run(SysAdminTransferContext sysAdminTransferContext) {
		try {
			ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
					.getBean("sysAttendCategoryService");
			String hql = "update com.landray.kmss.sys.attend.model.SysAttendCategory cate "
					+ "set cate.fdAppKey=:appKey "
					+ "where cate.fdAppName = '会议管理' or cate.fdAppName = 'Meeting Management' ";
			Query query = sysAttendCategoryService.getBaseDao()
					.getHibernateSession()
					.createQuery(hql);
			IExtension extension = AttendPlugin.getExtension(
					"com.landray.kmss.km.imeeting.model.KmImeetingMain");
			query.setParameter("appKey",
					Plugin.getParamValueString(extension, "modelKey"));
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferResult.OK;
	}

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		try {
			ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
					.getBean("sysAdminTransferTaskService");
			ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil
					.getBean("sysAttendCategoryService");
			String uuid = sysAdminTransferCheckContext.getUUID();
			List list = new ArrayList();
			list = sysAdminTransferTaskService.getBaseDao().findValue(null,
					"sysAdminTransferTask.fdUuid='" + uuid + "'", null);

			if (list.size() > 0) {
				SysAdminTransferTask sysAdminTransferTask = (SysAdminTransferTask) list
						.get(0);
				if (sysAdminTransferTask.getFdStatus() == 1) {
					return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
				}
			}

			String selectSql = "select count(*) from sys_attend_category where fd_app_name = '会议管理' or fd_app_name = 'Meeting Management'";
			List categoryList = sysAttendCategoryService.getBaseDao().getHibernateSession().createNativeQuery(selectSql).list();
			if (Long.parseLong(categoryList.get(0).toString()) == 0L) {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

}
