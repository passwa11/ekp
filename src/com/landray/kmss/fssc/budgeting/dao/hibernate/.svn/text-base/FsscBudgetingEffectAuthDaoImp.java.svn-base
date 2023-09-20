package com.landray.kmss.fssc.budgeting.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budgeting.dao.IFsscBudgetingEffectAuthDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingEffectAuth;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscBudgetingEffectAuthDaoImp extends BaseDaoImp implements IFsscBudgetingEffectAuthDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetingEffectAuth fsscBudgetingEffectAuth = (FsscBudgetingEffectAuth) modelObj;
        if (fsscBudgetingEffectAuth.getDocCreator() == null) {
            fsscBudgetingEffectAuth.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetingEffectAuth.getDocCreateTime() == null) {
            fsscBudgetingEffectAuth.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetingEffectAuth);
    }
}
