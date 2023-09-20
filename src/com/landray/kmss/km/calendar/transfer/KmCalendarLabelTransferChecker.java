package com.landray.kmss.km.calendar.transfer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.calendar.service.IKmCalendarAgendaLabelService;
import com.landray.kmss.km.calendar.service.IKmCalendarLabelService;
import com.landray.kmss.sys.admin.transfer.model.SysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferChecker;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTaskService;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferCheckResult;
import com.landray.kmss.util.SpringBeanUtil;

public class KmCalendarLabelTransferChecker
		implements ISysAdminTransferChecker {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@Override
	public SysAdminTransferCheckResult
			check(SysAdminTransferCheckContext sysAdminTransferCheckContext) {
		IKmCalendarLabelService labelService = (IKmCalendarLabelService) SpringBeanUtil
				.getBean("kmCalendarLabelService");
		IKmCalendarAgendaLabelService agendaLabelService = (IKmCalendarAgendaLabelService) SpringBeanUtil
				.getBean("kmCalendarAgendaLabelService");
		ISysAdminTransferTaskService sysAdminTransferTaskService = (ISysAdminTransferTaskService) SpringBeanUtil
				.getBean("sysAdminTransferTaskService");
		try {
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

			String labelSql = "select count(*) from km_calendar_label a where a.fd_model_name <>' ' and a.fd_model_name is not null and exists(select 1 from km_calendar_label b where a.fd_id<>b.fd_id and a.fd_creator_id=b.fd_creator_id and a.fd_model_name=b.fd_model_name)";
			List labelList = labelService.getBaseDao().getHibernateSession().createNativeQuery(labelSql).list();
			String agendaLabelSql = "select count(*) from km_calendar_agenda_label a where exists(select 1 from km_calendar_agenda_label b where a.fd_id<>b.fd_id and a.fd_model_name=b.fd_model_name)";
			List agendaLabelList = agendaLabelService.getBaseDao().getHibernateSession().createNativeQuery(agendaLabelSql).list();
			if (Long.parseLong(labelList.get(0).toString()) == 0L && Long
					.parseLong(agendaLabelList.get(0).toString()) == 0L) {
				return SysAdminTransferCheckResult.TASK_STATUS_RUNED;
			}

		} catch (Exception e) {
			logger.error("检查是否执行过旧数据迁移为空异常", e);
		}
		return SysAdminTransferCheckResult.TASK_STATUS_NOT_RUNED;
	}

}
