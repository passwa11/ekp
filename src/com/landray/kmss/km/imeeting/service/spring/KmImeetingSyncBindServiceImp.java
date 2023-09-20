package com.landray.kmss.km.imeeting.service.spring;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.imeeting.dao.IKmImeetingSyncBindDao;
import com.landray.kmss.km.imeeting.model.KmImeetingSyncBind;
import com.landray.kmss.km.imeeting.service.IKmImeetingSyncBindService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.util.IDGenerator;

/**
 * 同步绑定信息业务接口实现
 * 
 * @author
 * @version 1.0 2013-10-14
 */
public class KmImeetingSyncBindServiceImp extends BaseServiceImp implements
		IKmImeetingSyncBindService {

	private ISysOrgCoreService sysOrgCoreService;

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	@Override
    @SuppressWarnings("unchecked")
	public Date getSyncroDate(String personId, String appKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmImeetingSyncBind.fdSyncTimestamp");
		String where = "kmImeetingSyncBind.fdAppKey=:fdAppKey and kmImeetingSyncBind.fdOwner.fdId=:fdId";
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
	public KmImeetingSyncBind findByPersonAndAppKey(String personId,
			String appKey) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String where = "kmImeetingSyncBind.fdAppKey=:fdAppKey and kmImeetingSyncBind.fdOwner.fdId=:fdId";
		// where += " order by oauthBindData.fdOwner.fdId";
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdAppKey", appKey);
		hqlInfo.setParameter("fdId", personId);
		List rtnList = getBaseDao().findList(hqlInfo);
		return (!rtnList.isEmpty()) ? (KmImeetingSyncBind) rtnList.get(0)
				: null;

	}

	@Override
    public void updateSyncroDate(String personId, String appKey, Date date)
			throws Exception {
		KmImeetingSyncBind kmImeetingSyncBind = findByPersonAndAppKey(personId,
				appKey);
		if (kmImeetingSyncBind == null) {
			kmImeetingSyncBind = new KmImeetingSyncBind();
			kmImeetingSyncBind.setFdId(IDGenerator.generateID());
			kmImeetingSyncBind.setFdAppKey(appKey);
			kmImeetingSyncBind.setFdOwner(sysOrgCoreService
					.findByPrimaryKey(personId));
			kmImeetingSyncBind.setFdSyncTimestamp(date);
			add(kmImeetingSyncBind);
		} else {
			kmImeetingSyncBind.setFdSyncTimestamp(date);
			update(kmImeetingSyncBind);
		}
	}

	@Override
    public void deleteSyncroData(String personId, String appKey) {

		try {
			((IKmImeetingSyncBindDao) getBaseDao()).delete(appKey, personId);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

	}
}
