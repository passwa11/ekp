package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataAccountsDao;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.util.UserUtil;

public class EopBasedataAccountsDaoImp extends BaseTreeDaoImp implements IEopBasedataAccountsDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataAccounts eopBasedataAccounts = (EopBasedataAccounts) modelObj;
        if (eopBasedataAccounts.getDocCreator() == null) {
            eopBasedataAccounts.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataAccounts.getDocCreateTime() == null) {
            eopBasedataAccounts.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataAccounts);
    }
}
