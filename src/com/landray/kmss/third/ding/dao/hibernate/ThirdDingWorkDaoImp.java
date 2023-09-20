package com.landray.kmss.third.ding.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.third.ding.dao.IThirdDingWorkDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.third.ding.model.ThirdDingWork;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdDingWorkDaoImp extends BaseDaoImp implements IThirdDingWorkDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingWork thirdDingWork = (ThirdDingWork) modelObj;
        if (thirdDingWork.getDocCreator() == null) {
            thirdDingWork.setDocCreator(UserUtil.getUser());
        }
        if (thirdDingWork.getDocCreateTime() == null) {
            thirdDingWork.setDocCreateTime(new Date());
        }
        return super.add(thirdDingWork);
    }
}
