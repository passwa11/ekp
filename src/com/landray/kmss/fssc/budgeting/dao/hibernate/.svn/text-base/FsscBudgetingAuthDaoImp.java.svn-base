package com.landray.kmss.fssc.budgeting.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budgeting.dao.IFsscBudgetingAuthDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingAuth;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscBudgetingAuthDaoImp extends BaseDaoImp implements IFsscBudgetingAuthDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetingAuth fsscBudgetingAuth = (FsscBudgetingAuth) modelObj;
        if (fsscBudgetingAuth.getDocCreator() == null) {
            fsscBudgetingAuth.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetingAuth.getDocCreateTime() == null) {
            fsscBudgetingAuth.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetingAuth);
    }
}
