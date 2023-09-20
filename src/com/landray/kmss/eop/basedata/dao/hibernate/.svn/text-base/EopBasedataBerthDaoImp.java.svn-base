package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataBerthDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataBerthDaoImp extends BaseDaoImp implements IEopBasedataBerthDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataBerth eopBasedataBerth = (EopBasedataBerth) modelObj;
        if (eopBasedataBerth.getDocCreator() == null) {
            eopBasedataBerth.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataBerth.getDocCreateTime() == null) {
            eopBasedataBerth.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataBerth);
    }
}
