package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataExchangeRateDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataExchangeRateDaoImp extends BaseDaoImp implements IEopBasedataExchangeRateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataExchangeRate eopBasedataExchangeRate = (EopBasedataExchangeRate) modelObj;
        if (eopBasedataExchangeRate.getDocCreator() == null) {
            eopBasedataExchangeRate.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataExchangeRate.getDocCreateTime() == null) {
            eopBasedataExchangeRate.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataExchangeRate);
    }
}
