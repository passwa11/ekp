package com.landray.kmss.fssc.expense.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.expense.dao.IFsscExpenseBalanceDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseBalance;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscExpenseBalanceDaoImp extends BaseDaoImp implements IFsscExpenseBalanceDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscExpenseBalance fsscExpenseBalance = (FsscExpenseBalance) modelObj;
        if (fsscExpenseBalance.getDocCreator() == null) {
            fsscExpenseBalance.setDocCreator(UserUtil.getUser());
        }
        if (fsscExpenseBalance.getDocCreateTime() == null) {
            fsscExpenseBalance.setDocCreateTime(new Date());
        }
        return super.add(fsscExpenseBalance);
    }
}
