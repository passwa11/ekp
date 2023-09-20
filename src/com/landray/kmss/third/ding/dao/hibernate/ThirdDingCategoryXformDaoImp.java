package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingCategoryXformDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingCategoryXform;

public class ThirdDingCategoryXformDaoImp extends BaseDaoImp implements IThirdDingCategoryXformDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingCategoryXform thirdDingCategoryXform = (ThirdDingCategoryXform) modelObj;
        if (thirdDingCategoryXform.getDocCreateTime() == null) {
            thirdDingCategoryXform.setDocCreateTime(new Date());
        }
        return super.add(thirdDingCategoryXform);
    }
}
