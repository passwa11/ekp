package com.landray.kmss.third.ding.notify.queue.dao.hibernate;

import java.util.Calendar;

import org.hibernate.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.notify.queue.dao.IThirdDingNotifyQueueErrorDao;

public class ThirdDingNotifyQueueErrorDaoImp extends BaseDaoImp
		implements IThirdDingNotifyQueueErrorDao {

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.ding.notify.queue.model.ThirdDingNotifyQueueError where fdCreateTime<:date and fdRepeatHandle=0");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
