package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataStandardDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataStandard;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataStandardDaoImp extends BaseDaoImp implements IEopBasedataStandardDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataStandard eopBasedataStandard = (EopBasedataStandard) modelObj;
        if (eopBasedataStandard.getDocCreator() == null) {
            eopBasedataStandard.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataStandard.getDocCreateTime() == null) {
            eopBasedataStandard.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataStandard);
    }
}
