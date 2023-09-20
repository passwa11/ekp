package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;

import com.landray.kmss.util.UserUtil;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataContbodyDaoImp extends BaseDaoImp implements com.landray.kmss.eop.basedata.dao.IEopBasedataContbodyDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        com.landray.kmss.eop.basedata.model.EopBasedataContbody eopBasedataContbody = (com.landray.kmss.eop.basedata.model.EopBasedataContbody) modelObj;
        if (eopBasedataContbody.getDocCreator() == null) {
            eopBasedataContbody.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataContbody.getDocCreateTime() == null) {
            eopBasedataContbody.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataContbody);
    }
}
