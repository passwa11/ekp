package com.landray.kmss.third.welink.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.welink.dao.IThirdWelinkNotifyQueueErrDao;
import com.landray.kmss.third.welink.model.ThirdWelinkNotifyQueueErr;

public class ThirdWelinkNotifyQueueErrDaoImp extends BaseDaoImp implements IThirdWelinkNotifyQueueErrDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWelinkNotifyQueueErr thirdWelinkNotifyQueueErr = (ThirdWelinkNotifyQueueErr) modelObj;
        if (thirdWelinkNotifyQueueErr.getDocCreateTime() == null) {
            thirdWelinkNotifyQueueErr.setDocCreateTime(new Date());
        }
		if (thirdWelinkNotifyQueueErr.getDocAlterTime() == null) {
			thirdWelinkNotifyQueueErr.setDocAlterTime(new Date());
		}
        return super.add(thirdWelinkNotifyQueueErr);
    }

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		ThirdWelinkNotifyQueueErr thirdWelinkNotifyQueueErr = (ThirdWelinkNotifyQueueErr) modelObj;
		thirdWelinkNotifyQueueErr.setDocAlterTime(new Date());
		super.update(thirdWelinkNotifyQueueErr);
	}

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.welink.model.ThirdWelinkNotifyQueueErr where docCreateTime<:date and fdRepeatHandle>=5");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
