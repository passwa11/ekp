package com.landray.kmss.km.archives.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.km.archives.dao.IKmArchivesPeriodDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesPeriod;
import com.landray.kmss.common.dao.BaseDaoImp;

public class KmArchivesPeriodDaoImp extends BaseDaoImp implements IKmArchivesPeriodDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesPeriod kmArchivesPeriod = (KmArchivesPeriod) modelObj;
        if (kmArchivesPeriod.getDocCreator() == null) {
            kmArchivesPeriod.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesPeriod.getDocCreateTime() == null) {
            kmArchivesPeriod.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesPeriod);
    }
}
