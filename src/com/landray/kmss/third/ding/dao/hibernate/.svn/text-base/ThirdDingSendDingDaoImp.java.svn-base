package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingSendDingDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingSendDing;

public class ThirdDingSendDingDaoImp extends BaseDaoImp implements IThirdDingSendDingDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingSendDing thirdDingSendDing = (ThirdDingSendDing) modelObj;
        if (thirdDingSendDing.getDocCreateTime() == null) {
            thirdDingSendDing.setDocCreateTime(new Date());
        }
        return super.add(thirdDingSendDing);
    }
}
