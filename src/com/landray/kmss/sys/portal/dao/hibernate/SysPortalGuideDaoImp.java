package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalGuideDao;
import com.landray.kmss.sys.portal.model.SysPortalGuide;
import com.landray.kmss.util.UserUtil;

public class SysPortalGuideDaoImp extends BaseDaoImp implements
		ISysPortalGuideDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysPortalGuide xmodel = (SysPortalGuide) modelObj;
		if (xmodel.getDocCreator() == null) {
			xmodel.setDocCreator(UserUtil.getUser());
		}
		if (xmodel.getDocCreateTime() == null) {
			xmodel.setDocCreateTime(new Date());
		}
		return super.add(xmodel);
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysPortalGuide xmodel = (SysPortalGuide) modelObj;
		xmodel.setDocAlteror(UserUtil.getUser());
		xmodel.setDocAlterTime(new Date());
		super.update(modelObj);
	}
}
