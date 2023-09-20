package com.landray.kmss.fssc.budget.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budget.dao.IFsscBudgetAdjustMainDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscBudgetAdjustMainDaoImp extends BaseDaoImp implements IFsscBudgetAdjustMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetAdjustMain fsscBudgetAdjustMain = (FsscBudgetAdjustMain) modelObj;
        if (fsscBudgetAdjustMain.getDocCreator() == null) {
            fsscBudgetAdjustMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetAdjustMain.getDocCreateTime() == null) {
            fsscBudgetAdjustMain.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetAdjustMain);
    }
}
