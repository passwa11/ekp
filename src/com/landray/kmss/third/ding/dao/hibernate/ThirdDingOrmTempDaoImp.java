package com.landray.kmss.third.ding.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.ding.dao.IThirdDingOrmTempDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.ding.model.ThirdDingOrmTemp;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdDingOrmTempDaoImp extends BaseDaoImp implements IThirdDingOrmTempDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingOrmTemp thirdDingOrmTemp = (ThirdDingOrmTemp) modelObj;
        if (thirdDingOrmTemp.getDocCreator() == null) {
            thirdDingOrmTemp.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingOrmTemp.getDocCreateTime() == null) {
            thirdDingOrmTemp.setDocCreateTime(new Date());
        }
        return super.add(thirdDingOrmTemp);
    }
}
