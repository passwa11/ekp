package com.landray.kmss.third.feishu.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.feishu.dao.IThirdFeishuNotifyQueueErrDao;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr;

public class ThirdFeishuNotifyQueueErrDaoImp extends BaseDaoImp implements IThirdFeishuNotifyQueueErrDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdFeishuNotifyQueueErr thirdFeishuNotifyQueueErr = (ThirdFeishuNotifyQueueErr) modelObj;
        if (thirdFeishuNotifyQueueErr.getDocCreateTime() == null) {
            thirdFeishuNotifyQueueErr.setDocCreateTime(new Date());
        }
		thirdFeishuNotifyQueueErr.setDocAlterTime(new Date());
        return super.add(thirdFeishuNotifyQueueErr);
    }

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.feishu.model.ThirdFeishuNotifyQueueErr where docCreateTime<:date and fdRepeatHandle>=5");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
