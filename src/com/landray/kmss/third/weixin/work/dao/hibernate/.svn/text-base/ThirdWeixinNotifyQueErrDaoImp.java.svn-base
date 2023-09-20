package com.landray.kmss.third.weixin.work.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.weixin.work.dao.IThirdWeixinNotifyQueErrDao;
import com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyQueErr;

public class ThirdWeixinNotifyQueErrDaoImp extends BaseDaoImp implements IThirdWeixinNotifyQueErrDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdWeixinNotifyQueErr thirdWeixinNotifyQueErr = (ThirdWeixinNotifyQueErr) modelObj;
        if (thirdWeixinNotifyQueErr.getDocCreateTime() == null) {
            thirdWeixinNotifyQueErr.setDocCreateTime(new Date());
        }
        return super.add(thirdWeixinNotifyQueErr);
    }

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.weixin.work.model.ThirdWeixinNotifyQueErr where docCreateTime<:date and (fdRepeatHandle>=5 or fdFlag=0)");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
