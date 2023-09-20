package com.landray.kmss.third.ding.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.ding.dao.IThirdDingEventDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.ding.model.ThirdDingEvent;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdDingEventDaoImp extends BaseDaoImp implements IThirdDingEventDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingEvent thirdDingEvent = (ThirdDingEvent) modelObj;
        if (thirdDingEvent.getDocCreator() == null) {
            thirdDingEvent.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingEvent.getDocCreateTime() == null) {
            thirdDingEvent.setDocCreateTime(new Date());
        }
        return super.add(thirdDingEvent);
    }
}
