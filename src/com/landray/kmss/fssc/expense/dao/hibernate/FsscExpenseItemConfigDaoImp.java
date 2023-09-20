package com.landray.kmss.fssc.expense.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.fssc.expense.dao.IFsscExpenseItemConfigDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseItemConfig;
import com.landray.kmss.common.dao.BaseDaoImp;

public class FsscExpenseItemConfigDaoImp extends BaseDaoImp implements IFsscExpenseItemConfigDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        FsscExpenseItemConfig fsscExpenseItemConfig = (FsscExpenseItemConfig) modelObj;
        if (fsscExpenseItemConfig.getDocCreator() == null) {
            fsscExpenseItemConfig.setDocCreator(UserUtil.getUser());
        }
        if (fsscExpenseItemConfig.getDocCreateTime() == null) {
            fsscExpenseItemConfig.setDocCreateTime(new Date());
        }
        return super.add(fsscExpenseItemConfig);
    }
}
