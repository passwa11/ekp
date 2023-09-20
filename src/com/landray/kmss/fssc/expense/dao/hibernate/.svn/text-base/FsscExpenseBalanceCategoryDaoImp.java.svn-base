package com.landray.kmss.fssc.expense.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.expense.dao.IFsscExpenseBalanceCategoryDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory;
import com.landray.kmss.sys.simplecategory.dao.hibernate.SysSimpleCategoryDaoImp;

public class FsscExpenseBalanceCategoryDaoImp extends SysSimpleCategoryDaoImp implements IFsscExpenseBalanceCategoryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscExpenseBalanceCategory fsscExpenseBalanceCategory = (FsscExpenseBalanceCategory) modelObj;
        if (fsscExpenseBalanceCategory.getDocCreator() == null) {
            fsscExpenseBalanceCategory.setDocCreator(UserUtil.getUser());
        }
        if (fsscExpenseBalanceCategory.getDocCreateTime() == null) {
            fsscExpenseBalanceCategory.setDocCreateTime(new Date());
        }
        return super.add(fsscExpenseBalanceCategory);
    }
}
