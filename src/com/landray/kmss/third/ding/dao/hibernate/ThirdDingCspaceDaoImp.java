package com.landray.kmss.third.ding.dao.hibernate;

import java.util.Date;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.third.ding.dao.IThirdDingCspaceDao;
import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.third.ding.model.ThirdDingCspace;

public class ThirdDingCspaceDaoImp extends BaseDaoImp implements IThirdDingCspaceDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        ThirdDingCspace thirdDingCspace = (ThirdDingCspace) modelObj;
        if (thirdDingCspace.getDocCreateTime() == null) {
            thirdDingCspace.setDocCreateTime(new Date());
        }
        return super.add(thirdDingCspace);
    }
}
