package com.landray.kmss.km.archives.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.km.archives.dao.IKmArchivesRenewDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesRenew;
import com.landray.kmss.common.dao.BaseDaoImp;

public class KmArchivesRenewDaoImp extends BaseDaoImp implements IKmArchivesRenewDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesRenew kmArchivesRenew = (KmArchivesRenew) modelObj;
        if (kmArchivesRenew.getDocCreator() == null) {
            kmArchivesRenew.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesRenew.getDocCreateTime() == null) {
            kmArchivesRenew.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesRenew);
    }
}
