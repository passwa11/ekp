package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataTaxRateDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataTaxRate;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataTaxRateDaoImp extends BaseDaoImp implements IEopBasedataTaxRateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataTaxRate eopBasedataTaxRate = (EopBasedataTaxRate) modelObj;
        if (eopBasedataTaxRate.getDocCreator() == null) {
            eopBasedataTaxRate.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataTaxRate.getDocCreateTime() == null) {
            eopBasedataTaxRate.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataTaxRate);
    }
}
