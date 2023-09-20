package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCustomerDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataCustomerDaoImp extends BaseDaoImp implements IEopBasedataCustomerDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCustomer eopBasedataCustomer = (EopBasedataCustomer) modelObj;
        if (eopBasedataCustomer.getDocCreator() == null) {
            eopBasedataCustomer.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCustomer.getDocCreateTime() == null) {
            eopBasedataCustomer.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCustomer);
    }
}
