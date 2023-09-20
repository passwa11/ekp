package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.dao.BaseTreeDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.dao.IEopBasedataGoodDao;
import com.landray.kmss.eop.basedata.model.EopBasedataGood;
import com.landray.kmss.util.UserUtil;

import java.util.Date;

public class EopBasedataGoodDaoImp extends BaseTreeDaoImp implements IEopBasedataGoodDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataGood eopBasedataGood = (EopBasedataGood) modelObj;
        if (eopBasedataGood.getDocCreator() == null) {
            eopBasedataGood.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataGood.getDocCreateTime() == null) {
            eopBasedataGood.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataGood);
    }
}
