package com.landray.kmss.km.archives.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.km.archives.dao.IKmArchivesUnitDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesUnit;
import com.landray.kmss.common.dao.BaseDaoImp;

public class KmArchivesUnitDaoImp extends BaseDaoImp implements IKmArchivesUnitDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesUnit kmArchivesUnit = (KmArchivesUnit) modelObj;
        if (kmArchivesUnit.getDocCreator() == null) {
            kmArchivesUnit.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesUnit.getDocCreateTime() == null) {
            kmArchivesUnit.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesUnit);
    }
}
