package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataPassportDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataPassport;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataPassportDaoImp extends BaseDaoImp implements IEopBasedataPassportDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataPassport eopBasedataPassport = (EopBasedataPassport) modelObj;
        if (eopBasedataPassport.getDocCreator() == null) {
            eopBasedataPassport.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataPassport.getDocCreateTime() == null) {
            eopBasedataPassport.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataPassport);
    }
}
