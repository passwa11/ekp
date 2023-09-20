package com.landray.kmss.sys.handover.service.spring;

import java.util.List;

import org.hibernate.query.Query;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLog;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;
import com.landray.kmss.sys.handover.service.ISysHandoverConfigLogService;

public class SysHandoverConfigLogServiceImp extends BaseServiceImp implements ISysHandoverConfigLogService {

	@Override
    @SuppressWarnings("unchecked")
	public Long[] getCounts(SysHandoverConfigMain configMain, String moduleName) throws Exception {
		String hql = "select sum(configLog.fdCount), sum(configLog.fdIgnoreCount) from "
				+ SysHandoverConfigLog.class.getName()
				+ " configLog where configLog.fdModule =:fdModule and configLog.fdMain.fdId =:mainId group by configLog.fdModule";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdModule", moduleName);
		query.setParameter("mainId", configMain.getFdId());
		List<Object[]> result = query.list();
		Long[] counts = new Long[2];
		counts[0] = (Long) result.get(0)[0];
		counts[1] = (Long) result.get(0)[1];
		return counts;
	}

}
