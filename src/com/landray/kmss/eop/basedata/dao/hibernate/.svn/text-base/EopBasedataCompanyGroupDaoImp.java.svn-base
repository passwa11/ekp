package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCompanyGroupDao;
import com.landray.kmss.eop.basedata.model.EopBasedataCompanyGroup;
import com.landray.kmss.util.UserUtil;

public class EopBasedataCompanyGroupDaoImp extends BaseTreeDaoImp implements IEopBasedataCompanyGroupDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCompanyGroup eopBasedataCompanyGroup = (EopBasedataCompanyGroup) modelObj;
        if (eopBasedataCompanyGroup.getDocCreator() == null) {
            eopBasedataCompanyGroup.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCompanyGroup.getDocCreateTime() == null) {
            eopBasedataCompanyGroup.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCompanyGroup);
    }
}
