package com.landray.kmss.km.archives.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseCoreInnerDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.archives.dao.IKmArchivesFileTemplateDao;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;
import com.landray.kmss.util.UserUtil;

public class KmArchivesFileTemplateDaoImp extends BaseCoreInnerDaoImp
		implements IKmArchivesFileTemplateDao {

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		KmArchivesFileTemplate mainModel = (KmArchivesFileTemplate) modelObj;
		if (mainModel.getDocCreator() == null) {
			mainModel.setDocCreator(UserUtil.getUser());
		}
		if (mainModel.getDocCreateTime() == null) {
			mainModel.setDocCreateTime(new Date());
		}
		return super.add(mainModel);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmArchivesFileTemplate mainModel = (KmArchivesFileTemplate) modelObj;
		if (mainModel.getDocCreator() == null) {
			mainModel.setDocCreator(UserUtil.getUser());
		}
		if (mainModel.getDocCreateTime() == null) {
			mainModel.setDocCreateTime(new Date());
		}
		super.update(mainModel);
	}

}
