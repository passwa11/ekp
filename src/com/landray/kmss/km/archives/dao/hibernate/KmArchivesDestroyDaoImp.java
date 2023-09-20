package com.landray.kmss.km.archives.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.km.archives.dao.IKmArchivesDestroyDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesDestroy;
import com.landray.kmss.common.dao.BaseDaoImp;

public class KmArchivesDestroyDaoImp extends BaseDaoImp implements IKmArchivesDestroyDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesDestroy kmArchivesDestroy = (KmArchivesDestroy) modelObj;
        if (kmArchivesDestroy.getDocCreator() == null) {
            kmArchivesDestroy.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesDestroy.getDocCreateTime() == null) {
            kmArchivesDestroy.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesDestroy);
    }
}
