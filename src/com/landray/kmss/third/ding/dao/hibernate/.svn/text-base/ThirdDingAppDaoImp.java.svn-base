package com.landray.kmss.third.ding.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.ding.dao.IThirdDingAppDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.ding.model.ThirdDingApp;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdDingAppDaoImp extends BaseDaoImp implements IThirdDingAppDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingApp thirdDingApp = (ThirdDingApp) modelObj;
        if (thirdDingApp.getDocCreator() == null) {
            thirdDingApp.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingApp.getDocCreateTime() == null) {
            thirdDingApp.setDocCreateTime(new Date());
        }
        return super.add(thirdDingApp);
    }
}
