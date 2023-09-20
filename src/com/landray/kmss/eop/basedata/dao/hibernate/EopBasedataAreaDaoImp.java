package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataAreaDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataArea;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataAreaDaoImp extends BaseDaoImp implements IEopBasedataAreaDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataArea eopBasedataArea = (EopBasedataArea) modelObj;
        if (eopBasedataArea.getDocCreator() == null) {
            eopBasedataArea.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataArea.getDocCreateTime() == null) {
            eopBasedataArea.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataArea);
    }
}
