package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalLinkDao;
import com.landray.kmss.sys.portal.model.SysPortalLink;
import com.landray.kmss.util.UserUtil;

/**
 * 快捷方式数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalLinkDaoImp extends BaseDaoImp implements
		ISysPortalLinkDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysPortalLink sysPortalLink = (SysPortalLink) modelObj;
		if (sysPortalLink.getDocCreator() == null) {
			sysPortalLink.setDocCreator(UserUtil.getUser());
		}
		if (sysPortalLink.getDocCreateTime() == null) {
			sysPortalLink.setDocCreateTime(new Date());
		}
		return super.add(modelObj);
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		SysPortalLink sysPortalLink = (SysPortalLink) modelObj;
		sysPortalLink.setDocAlteror(UserUtil.getUser());
		sysPortalLink.setDocAlterTime(new Date());
		super.update(modelObj);
	}
}
