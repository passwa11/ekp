package com.landray.kmss.km.archives.dao.hibernate;

import com.landray.kmss.common.model.IBaseModel;
import java.util.Date;
import com.landray.kmss.km.archives.dao.IKmArchivesCategoryDao;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.sys.simplecategory.dao.hibernate.SysSimpleCategoryDaoImp;

public class KmArchivesCategoryDaoImp extends SysSimpleCategoryDaoImp implements IKmArchivesCategoryDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesCategory kmArchivesCategory = (KmArchivesCategory) modelObj;
        if (kmArchivesCategory.getDocCreator() == null) {
            kmArchivesCategory.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesCategory.getDocCreateTime() == null) {
            kmArchivesCategory.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesCategory);
    }
}
