package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalTreeDao;
import com.landray.kmss.sys.portal.model.SysPortalTree;
import com.landray.kmss.util.UserUtil;

/**
 * 多级数菜单数据访问接口实现
 * 
 * @author
 * @version 1.0 2013-09-23
 */
public class SysPortalTreeDaoImp extends BaseDaoImp implements
		ISysPortalTreeDao {
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysPortalTree xmodel = (SysPortalTree) modelObj;
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
		SysPortalTree xmodel = (SysPortalTree) modelObj;
		xmodel.setDocAlteror(UserUtil.getUser());
		xmodel.setDocAlterTime(new Date());
		super.update(modelObj);
	}
}
