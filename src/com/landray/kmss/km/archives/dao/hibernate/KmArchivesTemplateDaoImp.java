package com.landray.kmss.km.archives.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.dao.IKmArchivesTemplateDao;
import com.landray.kmss.km.archives.model.KmArchivesTemplate;
import com.landray.kmss.util.UserUtil;

public class KmArchivesTemplateDaoImp extends BaseDaoImp
		implements IKmArchivesTemplateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        KmArchivesTemplate kmArchivesTemplate = (KmArchivesTemplate) modelObj;
        if (kmArchivesTemplate.getDocCreator() == null) {
            kmArchivesTemplate.setDocCreator(UserUtil.getUser());
        }
        if (kmArchivesTemplate.getDocCreateTime() == null) {
            kmArchivesTemplate.setDocCreateTime(new Date());
        }
        return super.add(kmArchivesTemplate);
    }
}
