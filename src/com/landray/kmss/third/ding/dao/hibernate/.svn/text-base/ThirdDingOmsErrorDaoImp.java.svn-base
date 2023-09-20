package com.landray.kmss.third.ding.dao.hibernate;

import com.landray.kmss.third.ding.dao.IThirdDingOmsErrorDao;
import com.landray.kmss.common.dao.BaseDaoImp;

public class ThirdDingOmsErrorDaoImp extends BaseDaoImp implements IThirdDingOmsErrorDao {
    @Override
    public int deleteEkpRecord() throws Exception {
        return getSession().createQuery("delete from ThirdDingOmsError where fdOms='ekp'").executeUpdate();
    }
}
