package com.landray.kmss.third.im.kk.service.spring;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.im.kk.service.IKkImNotifyService;
import com.landray.kmss.util.TransactionUtils;
/**
 * kk消息推送业务接口实现
 * 
 * @author 
 * @version 1.0 2017-09-07
 */
public class KkImNotifyServiceImp extends ExtendDataServiceImp implements IKkImNotifyService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkImNotifyServiceImp.class);

	@Override
    public void updateStatus(String notifyTypeId, Integer notifyType, Integer fdStatus) {
		String hql = "UPDATE KkImNotify SET fdStatus = :fdStatus WHERE fdNotifyId =:fdNotifyId and fdType =:notifyType";
		this.getBaseDao().getHibernateSession().createQuery(hql)
		.setParameter("fdStatus", fdStatus)
		.setParameter("fdNotifyId", notifyTypeId)
		.setParameter("notifyType", notifyType)
		.executeUpdate();		
		this.getBaseDao().getHibernateSession().clear();
	}

	@Override
	public void deleteByNotifyId(String notifyId) {
		String hql = "DELETE FROM KkImNotify WHERE fdNotifyId = :fdNotifyId";
		this.getBaseDao().getHibernateSession().createQuery(hql)
		.setParameter("fdNotifyId", notifyId)
		.executeUpdate();		
		this.getBaseDao().getHibernateSession().clear();
	}

	@Override
	public void deleteByUserId(String notifyId, String userId) {
		String hql = "DELETE FROM KkImNotify WHERE fdNotifyId = :fdNotifyId and fdUserId = :fdUserId";
		this.getBaseDao().getHibernateSession().createQuery(hql)
		.setParameter("fdNotifyId", notifyId)
		.setParameter("fdUserId", userId)
		.executeUpdate();		
		this.getBaseDao().getHibernateSession().clear();
	}

	@Override
	public void deleteByUserAll(String notifyId, List<SysOrgPerson> person) {
		TransactionStatus status = null;
		try {
			if (null != person && person.size() > 0) {
				status = TransactionUtils.beginNewTransaction();
				List<String> idList = new ArrayList<String>();
				for (SysOrgPerson orgPerson : person) {
					idList.add(orgPerson.getFdId());
				}
				String hql = "DELETE FROM KkImNotify WHERE fdNotifyId = :fdNotifyId and fdUserId in(:idList)";
				this.getBaseDao().getHibernateSession().createQuery(hql).setParameter("fdNotifyId", notifyId)
						.setParameterList("idList", idList).executeUpdate();
				TransactionUtils.getTransactionManager().commit(status);
			}
		} catch (Exception e) {
			if (status != null) {
				try {
					TransactionUtils.getTransactionManager().rollback(status);
				} catch (Exception ex) {
					logger.error("事务回滚出错", ex);
				}
			}
			logger.debug("", e);
		}
	}

	@Override
	public void deleteByNotifyId(String notifyId, Integer notifyType) {
		String hql = "DELETE FROM KkImNotify WHERE fdNotifyId = :fdNotifyId and fdType =:notifyType";
		this.getBaseDao().getHibernateSession().createQuery(hql)
		.setParameter("fdNotifyId", notifyId)
		.setParameter("notifyType", notifyType)
		.executeUpdate();		
		this.getBaseDao().getHibernateSession().clear();
	}

}
