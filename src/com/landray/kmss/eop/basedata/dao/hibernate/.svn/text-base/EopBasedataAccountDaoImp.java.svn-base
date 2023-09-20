package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataAccountDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataAccount;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataAccountDaoImp extends BaseDaoImp implements IEopBasedataAccountDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataAccount eopBasedataAccount = (EopBasedataAccount) modelObj;
        if (eopBasedataAccount.getDocCreator() == null) {
            eopBasedataAccount.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataAccount.getDocCreateTime() == null) {
            eopBasedataAccount.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataAccount);
    }
}
