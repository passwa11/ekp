package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingDtemplateXformDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;

public class ThirdDingDtemplateXformDaoImp extends BaseDaoImp implements IThirdDingDtemplateXformDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingDtemplateXform thirdDingDtemplateXform = (ThirdDingDtemplateXform) modelObj;
        if (thirdDingDtemplateXform.getDocCreateTime() == null) {
            thirdDingDtemplateXform.setDocCreateTime(new Date());
        }
        return super.add(thirdDingDtemplateXform);
    }
}
