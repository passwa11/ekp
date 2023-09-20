package com.landray.kmss.fssc.budget.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.budget.dao.IFsscBudgetAdjustCategoryDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory;
import com.landray.kmss.sys.simplecategory.dao.hibernate.SysSimpleCategoryDaoImp;

public class FsscBudgetAdjustCategoryDaoImp extends SysSimpleCategoryDaoImp implements IFsscBudgetAdjustCategoryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscBudgetAdjustCategory fsscBudgetAdjustCategory = (FsscBudgetAdjustCategory) modelObj;
        if (fsscBudgetAdjustCategory.getDocCreator() == null) {
            fsscBudgetAdjustCategory.setDocCreator(UserUtil.getUser());
        }
        if (fsscBudgetAdjustCategory.getDocCreateTime() == null) {
            fsscBudgetAdjustCategory.setDocCreateTime(new Date());
        }
        return super.add(fsscBudgetAdjustCategory);
    }
}
