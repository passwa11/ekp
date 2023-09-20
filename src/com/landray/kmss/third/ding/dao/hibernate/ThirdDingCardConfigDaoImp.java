package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingCardConfigDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingCardConfig;

public class ThirdDingCardConfigDaoImp extends BaseDaoImp implements IThirdDingCardConfigDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingCardConfig thirdDingCardConfig = (ThirdDingCardConfig) modelObj;
        if (thirdDingCardConfig.getDocCreateTime() == null) {
            thirdDingCardConfig.setDocCreateTime(new Date());
        }
        return super.add(thirdDingCardConfig);
    }
}
