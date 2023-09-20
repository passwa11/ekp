package com.landray.kmss.third.im.kk.queue.dao.hibernate;

import java.util.Calendar;

import com.landray.kmss.third.im.kk.queue.dao.IKkNotifyQueueErrorDao;
import org.hibernate.Query;

import com.landray.kmss.common.dao.BaseDaoImp;

public class KkNotifyQueueErrorDaoImp extends BaseDaoImp
		implements IKkNotifyQueueErrorDao {

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.im.kk.queue.model.KkNotifyQueueError where fdCreateTime<:date and fdRepeatHandle=0");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
