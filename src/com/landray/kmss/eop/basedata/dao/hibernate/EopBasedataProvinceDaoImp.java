package com.landray.kmss.eop.basedata.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.eop.basedata.dao.IEopBasedataProvinceDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataProvince;
import com.landray.kmss.common.dao.BaseDaoImp;

public class EopBasedataProvinceDaoImp extends BaseDaoImp implements IEopBasedataProvinceDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        EopBasedataProvince eopBasedataProvince = (EopBasedataProvince) modelObj;
        if (eopBasedataProvince.getDocCreator() == null) {
            eopBasedataProvince.setDocCreator(UserUtil.getUser());
        }
        if (eopBasedataProvince.getDocCreateTime() == null) {
            eopBasedataProvince.setDocCreateTime(new Date());
        }
        return super.add(eopBasedataProvince);
    }
}
