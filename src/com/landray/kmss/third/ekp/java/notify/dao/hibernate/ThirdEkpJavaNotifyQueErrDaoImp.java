package com.landray.kmss.third.ekp.java.notify.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ekp.java.notify.dao.IThirdEkpJavaNotifyQueErrDao;
import com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyQueErr;

public class ThirdEkpJavaNotifyQueErrDaoImp extends BaseDaoImp implements IThirdEkpJavaNotifyQueErrDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdEkpJavaNotifyQueErr thirdEkpJavaNotifyQueErr = (ThirdEkpJavaNotifyQueErr) modelObj;
        if (thirdEkpJavaNotifyQueErr.getDocCreateTime() == null) {
            thirdEkpJavaNotifyQueErr.setDocCreateTime(new Date());
        }
        return super.add(thirdEkpJavaNotifyQueErr);
    }

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.ekp.java.notify.model.ThirdEkpJavaNotifyQueErr where docCreateTime<:date and (fdRepeatHandle>=5 or fdFlag=0)");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
