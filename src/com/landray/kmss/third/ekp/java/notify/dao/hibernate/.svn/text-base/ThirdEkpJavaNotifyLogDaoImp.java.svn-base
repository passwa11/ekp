package com.landray.kmss.third.ekp.java.notify.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ekp.java.notify.dao.IThirdEkpJavaNotifyLogDao;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyLog;

public class ThirdEkpJavaNotifyLogDaoImp extends BaseDaoImp implements IThirdEkpJavaNotifyLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdEkpJavaNotifyLog thirdEkpJavaNotifyLog = (ThirdEkpJavaNotifyLog) modelObj;
        if (thirdEkpJavaNotifyLog.getDocCreateTime() == null) {
            thirdEkpJavaNotifyLog.setDocCreateTime(new Date());
        }
        return super.add(thirdEkpJavaNotifyLog);
    }

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyLog where docCreateTime<:date");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
