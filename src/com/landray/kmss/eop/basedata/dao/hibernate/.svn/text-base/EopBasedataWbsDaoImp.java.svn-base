package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataWbsDao;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.util.UserUtil;

public class EopBasedataWbsDaoImp extends BaseTreeDaoImp implements IEopBasedataWbsDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataWbs eopBasedataWbs = (EopBasedataWbs) modelObj;
        if (eopBasedataWbs.getDocCreator() == null) {
            eopBasedataWbs.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataWbs.getDocCreateTime() == null) {
            eopBasedataWbs.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataWbs);
    }
}
