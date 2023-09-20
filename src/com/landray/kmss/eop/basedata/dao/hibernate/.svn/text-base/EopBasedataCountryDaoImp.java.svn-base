package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataCountryDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataCountry;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataCountryDaoImp extends BaseDaoImp implements IEopBasedataCountryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataCountry eopBasedataCountry = (EopBasedataCountry) modelObj;
        if (eopBasedataCountry.getDocCreator() == null) {
            eopBasedataCountry.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataCountry.getDocCreateTime() == null) {
            eopBasedataCountry.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataCountry);
    }
}
