package com.landray.kmss.km.archives.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.dao.IKmArchivesDestroyTemplateDao;
import com.landray.kmss.km.archives.model.KmArchivesDestroyTemplate;
import com.landray.kmss.util.UserUtil;

public class KmArchivesDestroyTemplateDaoImp extends BaseDaoImp
		implements IKmArchivesDestroyTemplateDao {

    @Override
    public String add(IBaseModel modelObj) throws Exception {
		KmArchivesDestroyTemplate kmArchivesDestroyTemplate = (KmArchivesDestroyTemplate) modelObj;
		if (kmArchivesDestroyTemplate.getDocCreator() == null) {
			kmArchivesDestroyTemplate.setDocCreator(UserUtil.getUser());
        }
		if (kmArchivesDestroyTemplate.getDocCreateTime() == null) {
			kmArchivesDestroyTemplate.setDocCreateTime(new Date());
        }
		return super.add(kmArchivesDestroyTemplate);
    }
}
