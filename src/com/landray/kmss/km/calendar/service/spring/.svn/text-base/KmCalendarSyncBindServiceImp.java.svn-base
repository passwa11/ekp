package com.landray.kmss.km.calendar.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.calendar.dao.IKmCalendarSyncBindDao;
import com.landray.kmss.km.calendar.model.KmCalendarSyncBind;
import com.landray.kmss.km.calendar.service.IKmCalendarSyncBindService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.util.IDGenerator;

/**
 * 同步绑定信息业务接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmCalendarSyncBindServiceImp extends BaseServiceImp implements
		IKmCalendarSyncBindService {

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
    @SuppressWarnings("unchecked")
	public Date getSyncroDate(String personId, String appKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmCalendarSyncBind.fdSyncTimestamp");
		String where = "kmCalendarSyncBind.fdAppKey=:fdAppKey and kmCalendarSyncBind.fdOwner.fdId=:fdId";
		// where += " order by oauthBindData.fdOwner.fdId";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdAppKey", appKey);
		hqlInfo.setParameter("fdId", personId);
		List rtnList = getBaseDao().findList(hqlInfo);
		;
		return (!rtnList.isEmpty()) ? (Date) rtnList.get(rtnList.size() - 1)
				: null;
	}

	@SuppressWarnings("unchecked")
	public KmCalendarSyncBind findByPersonAndAppKey(String personId,
			String appKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String where = "kmCalendarSyncBind.fdAppKey=:fdAppKey and kmCalendarSyncBind.fdOwner.fdId=:fdId";
		// where += " order by oauthBindData.fdOwner.fdId";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdAppKey", appKey);
		hqlInfo.setParameter("fdId", personId);
		List rtnList = getBaseDao().findList(hqlInfo);
		return (!rtnList.isEmpty()) ? (KmCalendarSyncBind) rtnList.get(0)
				: null;

	}

	@Override
    public void updateSyncroDate(String personId, String appKey, Date date)
			throws Exception {
		KmCalendarSyncBind kmCalendarSyncBind = findByPersonAndAppKey(personId,
				appKey);
		if (kmCalendarSyncBind == null) {
			kmCalendarSyncBind = new KmCalendarSyncBind();
			kmCalendarSyncBind.setFdId(IDGenerator.generateID());
			kmCalendarSyncBind.setFdAppKey(appKey);
			kmCalendarSyncBind.setFdOwner(sysOrgCoreService
					.findByPrimaryKey(personId));
			kmCalendarSyncBind.setFdSyncTimestamp(date);
			add(kmCalendarSyncBind);
		} else {
			kmCalendarSyncBind.setFdSyncTimestamp(date);
			update(kmCalendarSyncBind);
		}
	}

	@Override
    public void deleteSyncroData(String personId, String appKey) {

		try {
			((IKmCalendarSyncBindDao) getBaseDao()).delete(appKey, personId);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

	}
}
