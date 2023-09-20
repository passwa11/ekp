package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalNavDao;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.util.UserUtil;

/**
 * 系统导航数据访问接口实现
 */
public class SysPortalNavDaoImp extends BaseDaoImp implements
		ISysPortalNavDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysPortalNav xmodel = (SysPortalNav) modelObj;
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
		SysPortalNav xmodel = (SysPortalNav) modelObj;
		xmodel.setDocAlteror(UserUtil.getUser());
		xmodel.setDocAlterTime(new Date());
		super.update(modelObj);
	}
}
