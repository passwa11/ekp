package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataOutTaxDao;
import com.landray.kmss.eop.basedata.model.EopBasedataOutTax;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class EopBasedataOutTaxDaoImp extends BaseDaoImp implements IEopBasedataOutTaxDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataOutTax eopBasedataOutTax = (EopBasedataOutTax) modelObj;
        if (eopBasedataOutTax.getDocCreator() == null) {
            eopBasedataOutTax.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataOutTax.getDocCreateTime() == null) {
            eopBasedataOutTax.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataOutTax);
    }
}
