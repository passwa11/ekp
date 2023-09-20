package com.landray.kmss.fssc.budget.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budget.dao.IFsscBudgetAdjustLogDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustLog;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscBudgetAdjustLogDaoImp extends BaseDaoImp implements IFsscBudgetAdjustLogDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetAdjustLog fsscBudgetAdjustLog = (FsscBudgetAdjustLog) modelObj;
        if (fsscBudgetAdjustLog.getDocCreator() == null) {
            fsscBudgetAdjustLog.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetAdjustLog.getDocCreateTime() == null) {
            fsscBudgetAdjustLog.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetAdjustLog);
    }
}
