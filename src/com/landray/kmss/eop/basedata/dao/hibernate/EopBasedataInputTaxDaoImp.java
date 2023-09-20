package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataInputTaxDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataInputTax;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataInputTaxDaoImp extends BaseDaoImp implements IEopBasedataInputTaxDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataInputTax eopBasedataInputTax = (EopBasedataInputTax) modelObj;
        if (eopBasedataInputTax.getDocCreator() == null) {
            eopBasedataInputTax.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataInputTax.getDocCreateTime() == null) {
            eopBasedataInputTax.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataInputTax);
    }
}
