package com.landray.kmss.third.welink.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.welink.dao.IThirdWelinkNotifyLogDao;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyLog;

public class ThirdWelinkNotifyLogDaoImp extends BaseDaoImp implements IThirdWelinkNotifyLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWelinkNotifyLog thirdWelinkNotifyLog = (ThirdWelinkNotifyLog) modelObj;
        if (thirdWelinkNotifyLog.getDocCreateTime() == null) {
            thirdWelinkNotifyLog.setDocCreateTime(new Date());
        }

        return super.add(thirdWelinkNotifyLog);
    }

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.welink.model.ThirdWelinkNotifyLog where docCreateTime<:date");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
