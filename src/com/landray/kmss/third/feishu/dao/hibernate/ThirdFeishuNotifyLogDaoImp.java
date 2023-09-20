package com.landray.kmss.third.feishu.dao.hibernate;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.feishu.dao.IThirdFeishuNotifyLogDao;
import com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog;

public class ThirdFeishuNotifyLogDaoImp extends BaseDaoImp implements IThirdFeishuNotifyLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdFeishuNotifyLog thirdFeishuNotifyLog = (ThirdFeishuNotifyLog) modelObj;
        if (thirdFeishuNotifyLog.getDocCreateTime() == null) {
            thirdFeishuNotifyLog.setDocCreateTime(new Date());
        }
        return super.add(thirdFeishuNotifyLog);
    }

	@Override
	public void clear(int days) throws Exception {

		Query query = super.getSession()
				.createQuery(
						"delete from com.landray.kmss.third.feishu.model.ThirdFeishuNotifyLog where docCreateTime<:date");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -days);
		query.setParameter("date", c.getTime());
		query.executeUpdate();

	}
}
