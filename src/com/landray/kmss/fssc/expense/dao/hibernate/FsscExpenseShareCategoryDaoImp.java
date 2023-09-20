package com.landray.kmss.fssc.expense.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.expense.dao.IFsscExpenseShareCategoryDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory;
import com.landray.kmss.sys.simplecategory.dao.hibernate.SysSimpleCategoryDaoImp;

public class FsscExpenseShareCategoryDaoImp extends SysSimpleCategoryDaoImp implements IFsscExpenseShareCategoryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscExpenseShareCategory fsscExpenseShareCategory = (FsscExpenseShareCategory) modelObj;
        if (fsscExpenseShareCategory.getDocCreator() == null) {
            fsscExpenseShareCategory.setDocCreator(UserUtil.getUser());
        }
        if (fsscExpenseShareCategory.getDocCreateTime() == null) {
            fsscExpenseShareCategory.setDocCreateTime(new Date());
        }
        return super.add(fsscExpenseShareCategory);
    }
}
