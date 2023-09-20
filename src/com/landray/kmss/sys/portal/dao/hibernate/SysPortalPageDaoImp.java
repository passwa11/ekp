package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalPageDao;
import com.landray.kmss.sys.portal.model.SysPortalPage;
import com.landray.kmss.util.UserUtil;

/**
 * 主文档数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-07-18
 */
public class SysPortalPageDaoImp extends BaseDaoImp implements
		ISysPortalPageDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysPortalPage xmodel = (SysPortalPage) modelObj;
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
		SysPortalPage xmodel = (SysPortalPage) modelObj;
		xmodel.setDocAlteror(UserUtil.getUser());
		xmodel.setDocAlterTime(new Date());
		super.update(modelObj);
	}

}
