package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Calendar;
import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingCardLogDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingCardLog;
import org.hibernate.Query;

public class ThirdDingCardLogDaoImp extends BaseDaoImp implements IThirdDingCardLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingCardLog thirdDingCardLog = (ThirdDingCardLog) modelObj;
        if (thirdDingCardLog.getDocCreateTime() == null) {
            thirdDingCardLog.setDocCreateTime(new Date());
        }
        return super.add(thirdDingCardLog);
    }

    @Override
    public void clear(int days) throws Exception {
        Query query = super.getSession()
                .createQuery("delete from com.landray.kmss.third.ding.model.ThirdDingCardLog where docCreateTime<:date");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -days);
        query.setParameter("date", c.getTime());
        query.executeUpdate();
    }
}
