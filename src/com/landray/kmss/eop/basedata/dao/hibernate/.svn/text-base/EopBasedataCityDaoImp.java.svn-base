package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCityDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataCity;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataCityDaoImp extends BaseDaoImp implements IEopBasedataCityDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCity eopBasedataCity = (EopBasedataCity) modelObj;
        if (eopBasedataCity.getDocCreator() == null) {
            eopBasedataCity.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCity.getDocCreateTime() == null) {
            eopBasedataCity.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCity);
    }
}
