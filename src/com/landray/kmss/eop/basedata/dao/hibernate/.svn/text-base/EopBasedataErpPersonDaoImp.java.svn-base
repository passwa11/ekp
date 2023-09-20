package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataErpPersonDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataErpPerson;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataErpPersonDaoImp extends BaseDaoImp implements IEopBasedataErpPersonDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataErpPerson eopBasedataErpPerson = (EopBasedataErpPerson) modelObj;
        if (eopBasedataErpPerson.getDocCreator() == null) {
            eopBasedataErpPerson.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataErpPerson.getDocCreateTime() == null) {
            eopBasedataErpPerson.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataErpPerson);
    }
}
