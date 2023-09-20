package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataSpecialItemDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataSpecialItem;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataSpecialItemDaoImp extends BaseDaoImp implements IEopBasedataSpecialItemDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataSpecialItem eopBasedataSpecialItem = (EopBasedataSpecialItem) modelObj;
        if (eopBasedataSpecialItem.getDocCreator() == null) {
            eopBasedataSpecialItem.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataSpecialItem.getDocCreateTime() == null) {
            eopBasedataSpecialItem.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataSpecialItem);
    }
}
