package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingCalendarDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingCalendar;

public class ThirdDingCalendarDaoImp extends BaseDaoImp implements IThirdDingCalendarDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingCalendar thirdDingCalendar = (ThirdDingCalendar) modelObj;
        if (thirdDingCalendar.getDocCreateTime() == null) {
            thirdDingCalendar.setDocCreateTime(new Date());
        }
        return super.add(thirdDingCalendar);
    }
}
