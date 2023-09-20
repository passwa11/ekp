package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataFundDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataFund;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataFundDaoImp extends BaseDaoImp implements IEopBasedataFundDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataFund eopBasedataFund = (EopBasedataFund) modelObj;
        if (eopBasedataFund.getDocCreator() == null) {
            eopBasedataFund.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataFund.getDocCreateTime() == null) {
            eopBasedataFund.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataFund);
    }
}
