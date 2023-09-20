package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataPayBankDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataPayBankDaoImp extends BaseDaoImp implements IEopBasedataPayBankDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataPayBank eopBasedataPayBank = (EopBasedataPayBank) modelObj;
        if (eopBasedataPayBank.getDocCreator() == null) {
            eopBasedataPayBank.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataPayBank.getDocCreateTime() == null) {
            eopBasedataPayBank.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataPayBank);
    }
}
