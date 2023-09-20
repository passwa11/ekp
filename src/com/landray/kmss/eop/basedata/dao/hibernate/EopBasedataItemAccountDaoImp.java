package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataItemAccountDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataItemAccount;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataItemAccountDaoImp extends BaseDaoImp implements IEopBasedataItemAccountDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataItemAccount eopBasedataItemAccount = (EopBasedataItemAccount) modelObj;
        if (eopBasedataItemAccount.getDocCreator() == null) {
            eopBasedataItemAccount.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataItemAccount.getDocCreateTime() == null) {
            eopBasedataItemAccount.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataItemAccount);
    }
}
