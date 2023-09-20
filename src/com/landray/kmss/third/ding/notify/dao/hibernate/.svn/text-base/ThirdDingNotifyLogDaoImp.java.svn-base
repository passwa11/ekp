package com.landray.kmss.third.ding.notify.dao.hibernate;

import java.util.Calendar;

import org.hibernate.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.notify.dao.IThirdDingNotifyLogDao;

public class ThirdDingNotifyLogDaoImp extends BaseDaoImp implements IThirdDingNotifyLogDao {

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.ding.notify.model.ThirdDingNotifyLog where fdSendTime<:date");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
