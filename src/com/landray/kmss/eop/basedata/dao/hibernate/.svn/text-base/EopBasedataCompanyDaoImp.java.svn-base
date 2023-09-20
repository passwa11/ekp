package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCompanyDao;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.util.UserUtil;

public class EopBasedataCompanyDaoImp extends BaseDaoImp implements IEopBasedataCompanyDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCompany eopBasedataCompany = (EopBasedataCompany) modelObj;
        if (eopBasedataCompany.getDocCreator() == null) {
            eopBasedataCompany.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCompany.getDocCreateTime() == null) {
            eopBasedataCompany.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCompany);
    }
}
