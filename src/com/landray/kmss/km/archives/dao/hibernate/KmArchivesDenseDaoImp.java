package com.landray.kmss.km.archives.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.km.archives.dao.IKmArchivesDenseDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.common.dao.BaseDaoImp;

public class KmArchivesDenseDaoImp extends BaseDaoImp implements IKmArchivesDenseDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesDense kmArchivesDense = (KmArchivesDense) modelObj;
        if (kmArchivesDense.getDocCreator() == null) {
            kmArchivesDense.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesDense.getDocCreateTime() == null) {
            kmArchivesDense.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesDense);
    }
}
