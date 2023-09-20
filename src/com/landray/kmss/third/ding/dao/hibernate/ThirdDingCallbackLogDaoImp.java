package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingCallbackLogDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingCallbackLog;
import org.hibernate.Query;

public class ThirdDingCallbackLogDaoImp extends BaseDaoImp implements IThirdDingCallbackLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingCallbackLog thirdDingCallbackLog = (ThirdDingCallbackLog) modelObj;
        if (thirdDingCallbackLog.getDocCreateTime() == null) {
            thirdDingCallbackLog.setDocCreateTime(new Date());
        }
        return super.add(thirdDingCallbackLog);
    }

    @Override
    public void clear(int days) throws Exception {
        Query query = super.getSession()
                .createQuery("delete from com.landray.kmss.third.ding.model.ThirdDingCallbackLog where docCreateTime<:date");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -days);
        query.setParameter("date", c.getTime());
        query.executeUpdate();
    }
}
