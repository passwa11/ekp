package com.landray.kmss.fssc.budget.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budget.dao.IFsscBudgetMainDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budget.model.FsscBudgetMain;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscBudgetMainDaoImp extends BaseDaoImp implements IFsscBudgetMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetMain fsscBudgetMain = (FsscBudgetMain) modelObj;
        if (fsscBudgetMain.getDocCreator() == null) {
            fsscBudgetMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetMain.getDocCreateTime() == null) {
            fsscBudgetMain.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetMain);
    }
}
