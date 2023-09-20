package com.landray.kmss.fssc.budgeting.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budgeting.dao.IFsscBudgetingPeriodDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingPeriod;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscBudgetingPeriodDaoImp extends BaseDaoImp implements IFsscBudgetingPeriodDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetingPeriod fsscBudgetingPeriod = (FsscBudgetingPeriod) modelObj;
        if (fsscBudgetingPeriod.getDocCreator() == null) {
            fsscBudgetingPeriod.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetingPeriod.getDocCreateTime() == null) {
            fsscBudgetingPeriod.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetingPeriod);
    }
}
