package com.landray.kmss.fssc.budgeting.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budgeting.dao.IFsscBudgetingApprovalAuthDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalAuth;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscBudgetingApprovalAuthDaoImp extends BaseDaoImp implements IFsscBudgetingApprovalAuthDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetingApprovalAuth fsscBudgetingApprovalAuth = (FsscBudgetingApprovalAuth) modelObj;
        if (fsscBudgetingApprovalAuth.getDocCreator() == null) {
            fsscBudgetingApprovalAuth.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetingApprovalAuth.getDocCreateTime() == null) {
            fsscBudgetingApprovalAuth.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetingApprovalAuth);
    }
}
