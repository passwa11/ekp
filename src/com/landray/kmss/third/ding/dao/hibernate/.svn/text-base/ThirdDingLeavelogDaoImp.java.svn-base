package com.landray.kmss.third.ding.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.ding.dao.IThirdDingLeavelogDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdDingLeavelogDaoImp extends BaseDaoImp implements IThirdDingLeavelogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingLeavelog thirdDingLeavelog = (ThirdDingLeavelog) modelObj;
        if (thirdDingLeavelog.getDocCreator() == null) {
            thirdDingLeavelog.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingLeavelog.getDocCreateTime() == null) {
            thirdDingLeavelog.setDocCreateTime(new Date());
        }
        return super.add(thirdDingLeavelog);
    }
}
