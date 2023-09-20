package com.landray.kmss.km.archives.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.dao.IKmArchivesMainDao;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.util.UserUtil;

public class KmArchivesMainDaoImp extends ExtendDataDaoImp
		implements IKmArchivesMainDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesMain kmArchivesMain = (KmArchivesMain) modelObj;
        if (kmArchivesMain.getDocCreator() == null) {
            kmArchivesMain.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesMain.getDocCreateTime() == null) {
            kmArchivesMain.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesMain);
    }
}
