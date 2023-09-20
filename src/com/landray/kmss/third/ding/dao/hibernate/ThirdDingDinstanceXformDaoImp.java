package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingDinstanceXformDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingDinstanceXform;

public class ThirdDingDinstanceXformDaoImp extends BaseDaoImp implements IThirdDingDinstanceXformDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingDinstanceXform thirdDingDinstanceXform = (ThirdDingDinstanceXform) modelObj;
        if (thirdDingDinstanceXform.getDocCreateTime() == null) {
            thirdDingDinstanceXform.setDocCreateTime(new Date());
        }
        return super.add(thirdDingDinstanceXform);
    }
}
