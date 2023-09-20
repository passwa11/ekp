package com.landray.kmss.fssc.expense.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.expense.dao.IFsscExpenseShareMainDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseShareMain;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscExpenseShareMainDaoImp extends BaseDaoImp implements IFsscExpenseShareMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscExpenseShareMain fsscExpenseShareMain = (FsscExpenseShareMain) modelObj;
        if (fsscExpenseShareMain.getDocCreator() == null) {
            fsscExpenseShareMain.setDocCreator(UserUtil.getUser());
        }
        if (fsscExpenseShareMain.getDocCreateTime() == null) {
            fsscExpenseShareMain.setDocCreateTime(new Date());
        }
        return super.add(fsscExpenseShareMain);
    }
}
