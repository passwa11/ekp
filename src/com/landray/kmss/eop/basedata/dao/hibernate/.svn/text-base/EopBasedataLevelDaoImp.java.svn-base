package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataLevelDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataLevel;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataLevelDaoImp extends BaseDaoImp implements IEopBasedataLevelDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataLevel eopBasedataLevel = (EopBasedataLevel) modelObj;
        if (eopBasedataLevel.getDocCreator() == null) {
            eopBasedataLevel.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataLevel.getDocCreateTime() == null) {
            eopBasedataLevel.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataLevel);
    }
}
