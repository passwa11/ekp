package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingCardMappingDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingCardMapping;

public class ThirdDingCardMappingDaoImp extends BaseDaoImp implements IThirdDingCardMappingDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingCardMapping thirdDingCardMapping = (ThirdDingCardMapping) modelObj;
        if (thirdDingCardMapping.getDocCreateTime() == null) {
            thirdDingCardMapping.setDocCreateTime(new Date());
        }
        return super.add(thirdDingCardMapping);
    }
}
