package com.landray.kmss.km.archives.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.km.archives.dao.IKmArchivesLibraryDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesLibrary;
import com.landray.kmss.common.dao.BaseDaoImp;

public class KmArchivesLibraryDaoImp extends BaseDaoImp implements IKmArchivesLibraryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesLibrary kmArchivesLibrary = (KmArchivesLibrary) modelObj;
        if (kmArchivesLibrary.getDocCreator() == null) {
            kmArchivesLibrary.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesLibrary.getDocCreateTime() == null) {
            kmArchivesLibrary.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesLibrary);
    }
}
