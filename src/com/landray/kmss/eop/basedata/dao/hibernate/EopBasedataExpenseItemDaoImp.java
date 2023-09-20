package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataExpenseItemDao;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.util.UserUtil;

public class EopBasedataExpenseItemDaoImp extends BaseTreeDaoImp implements IEopBasedataExpenseItemDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataExpenseItem eopBasedataExpenseItem = (EopBasedataExpenseItem) modelObj;
        if (eopBasedataExpenseItem.getDocCreator() == null) {
            eopBasedataExpenseItem.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataExpenseItem.getDocCreateTime() == null) {
            eopBasedataExpenseItem.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataExpenseItem);
    }
}
