package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataItemBudgetDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataItemBudget;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataItemBudgetDaoImp extends BaseDaoImp implements IEopBasedataItemBudgetDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataItemBudget eopBasedataItemBudget = (EopBasedataItemBudget) modelObj;
        if (eopBasedataItemBudget.getDocCreator() == null) {
            eopBasedataItemBudget.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataItemBudget.getDocCreateTime() == null) {
            eopBasedataItemBudget.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataItemBudget);
    }
}
