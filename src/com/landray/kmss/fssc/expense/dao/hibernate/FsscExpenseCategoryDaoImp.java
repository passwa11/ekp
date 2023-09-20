package com.landray.kmss.fssc.expense.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.expense.dao.IFsscExpenseCategoryDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseCategory;
import com.landray.kmss.sys.simplecategory.dao.hibernate.SysSimpleCategoryDaoImp;

public class FsscExpenseCategoryDaoImp extends SysSimpleCategoryDaoImp implements IFsscExpenseCategoryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscExpenseCategory fsscExpenseCategory = (FsscExpenseCategory) modelObj;
        if (fsscExpenseCategory.getDocCreator() == null) {
            fsscExpenseCategory.setDocCreator(UserUtil.getUser());
        }
        if (fsscExpenseCategory.getDocCreateTime() == null) {
            fsscExpenseCategory.setDocCreateTime(new Date());
        }
        return super.add(fsscExpenseCategory);
    }
}
