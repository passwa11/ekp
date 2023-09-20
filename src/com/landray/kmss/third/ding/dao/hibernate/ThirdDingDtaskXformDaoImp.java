package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingDtaskXformDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingDtaskXform;

public class ThirdDingDtaskXformDaoImp extends BaseDaoImp implements IThirdDingDtaskXformDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingDtaskXform thirdDingDtaskXform = (ThirdDingDtaskXform) modelObj;
        if (thirdDingDtaskXform.getDocCreateTime() == null) {
            thirdDingDtaskXform.setDocCreateTime(new Date());
        }
        return super.add(thirdDingDtaskXform);
    }
}
