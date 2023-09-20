package com.landray.kmss.km.archives.service.spring;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.model.KmArchivesBorrow;
import com.landray.kmss.km.archives.util.KmArchivesConstant;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.lbpm.pvm.event.EngineEvent;
import com.landray.kmss.sys.lbpm.pvm.event.ProcessEndEvent;
import com.landray.kmss.util.SpringBeanUtil;

public class KmArchivesBorrowListener implements IEventListener {

	@Override
	public void handleEvent(EventExecutionContext execution, String parameter)
			throws Exception {
		IBaseModel mainModel = execution.getMainModel();
		EngineEvent event = execution.getEvent();
		if (mainModel instanceof KmArchivesBorrow) {
			KmArchivesBorrow kmArchivesBorrow = (KmArchivesBorrow) mainModel;
			// 流程结束
			if (event instanceof ProcessEndEvent) {
				String hql = "update com.landray.kmss.km.archives.model.KmArchivesDetails kmArchivesDetails set kmArchivesDetails.fdStatus=:fdStatus where kmArchivesDetails.docMain=:docMain";
				IBaseDao baseDao = (IBaseDao) SpringBeanUtil
						.getBean("KmssBaseDao");
				Query query = baseDao.getHibernateSession().createQuery(hql);
				query.setParameter("fdStatus",
						KmArchivesConstant.BORROW_STATUS_LOANING);
				query.setParameter("docMain", kmArchivesBorrow);
				query.executeUpdate();
			}
		}

	}

}
