package com.landray.kmss.fssc.budgeting.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budgeting.dao.IFsscBudgetingOrgDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingOrg;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscBudgetingOrgDaoImp extends BaseDaoImp implements IFsscBudgetingOrgDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetingOrg fsscBudgetingOrg = (FsscBudgetingOrg) modelObj;
        if (fsscBudgetingOrg.getDocCreator() == null) {
            fsscBudgetingOrg.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetingOrg.getDocCreateTime() == null) {
            fsscBudgetingOrg.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetingOrg);
    }
}
