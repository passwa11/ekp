package com.landray.kmss.km.archives.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.dao.IKmArchivesAppraiseTemplateDao;
import com.landray.kmss.km.archives.model.KmArchivesAppraiseTemplate;
import com.landray.kmss.util.UserUtil;

public class KmArchivesAppraiseTemplateDaoImp extends BaseDaoImp
		implements IKmArchivesAppraiseTemplateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
		KmArchivesAppraiseTemplate kmArchivesAppraiseTemplate = (KmArchivesAppraiseTemplate) modelObj;
		if (kmArchivesAppraiseTemplate.getDocCreator() == null) {
			kmArchivesAppraiseTemplate.setDocCreator(UserUtil.getUser());
        }
		if (kmArchivesAppraiseTemplate.getDocCreateTime() == null) {
			kmArchivesAppraiseTemplate.setDocCreateTime(new Date());
        }
		return super.add(kmArchivesAppraiseTemplate);
    }
}
