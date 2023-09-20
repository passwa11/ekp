package com.landray.kmss.third.weixin.work.dao.hibernate;

import java.util.Calendar;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinNotifyLogDao;

public class ThirdWeixinNotifyLogDaoImp extends BaseDaoImp implements IThirdWeixinNotifyLogDao {

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyLog where fdReqDate<:date");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
