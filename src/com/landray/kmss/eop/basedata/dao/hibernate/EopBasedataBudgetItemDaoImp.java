package com.landray.kmss.eop.basedata.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataBudgetItemDao;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.util.UserUtil;

public class EopBasedataBudgetItemDaoImp extends BaseTreeDaoImp implements IEopBasedataBudgetItemDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataBudgetItem eopBasedataBudgetItem = (EopBasedataBudgetItem) modelObj;
        if (eopBasedataBudgetItem.getDocCreator() == null) {
            eopBasedataBudgetItem.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataBudgetItem.getDocCreateTime() == null) {
            eopBasedataBudgetItem.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataBudgetItem);
    }
}
