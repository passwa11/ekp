package com.landray.kmss.km.calendar.transfer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.km.calendar.service.IKmCalendarAuthService;
import com.landray.kmss.km.calendar.service.IKmCalendarMainService;
import com.landray.kmss.sys.admin.transfer.service.ISysAdminTransferTask;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferContext;
import com.landray.kmss.sys.admin.transfer.service.spring.SysAdminTransferResult;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 重新计算权限
 */
public class KmCalendarAuthTransferTask implements ISysAdminTransferTask {

	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public SysAdminTransferResult run(SysAdminTransferContext sysAdminTransferContext) {
		IKmCalendarMainService kmCalendarMainService = (IKmCalendarMainService) SpringBeanUtil
				.getBean("kmCalendarMainService");
		try {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -6);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("kmCalendarMain.docCreateTime > :docCreateTime ");
			hqlInfo.setParameter("docCreateTime", c.getTime());
			List<KmCalendarMain> calendars = kmCalendarMainService.findList(hqlInfo);
			for (KmCalendarMain calendar : calendars) {
				refreshAuth(calendar);
				kmCalendarMainService.getBaseDao().update(calendar);
			}
		} catch (Exception e) {
			logger.error("执行旧数据迁移为空异常", e);
		}
		return SysAdminTransferResult.OK;
	}

	private void refreshAuth(KmCalendarMain kmcalendar) throws Exception {
		IKmCalendarAuthService kmCalendarAuthService = (IKmCalendarAuthService) SpringBeanUtil
				.getBean("kmCalendarAuthService");
		KmCalendarAuth kmCalendarAuth = kmCalendarAuthService.findByPerson(kmcalendar.getDocOwner().getFdId());
		if (kmCalendarAuth != null) {
			List<SysOrgElement> readersList = new ArrayList<>();
			List<SysOrgElement> readers = kmCalendarAuth.getAuthReaders();
			readersList.addAll(readers);
			if (!readersList.contains(kmcalendar.getDocCreator())) {
				readersList.add(kmcalendar.getDocCreator());
			}
			if (kmcalendar.getAuthReaders() == null || kmcalendar.getAuthReaders().isEmpty()) {
				kmcalendar.setAuthReaders(new ArrayList());
				kmcalendar.getAuthReaders().addAll(readersList);
			}
			List<SysOrgElement> modifiersList = new ArrayList<>();
			List<SysOrgElement> modifiers = kmCalendarAuth.getAuthModifiers();
			modifiersList.addAll(modifiers);
			if (!modifiersList.contains(kmcalendar.getDocCreator())) {
				modifiersList.add(kmcalendar.getDocCreator());
			}
			if (kmcalendar.getAuthEditors() == null || kmcalendar.getAuthEditors().isEmpty()) {
				kmcalendar.setAuthEditors(new ArrayList());
				kmcalendar.getAuthEditors().addAll(modifiersList);
			}
		}
	}

}
