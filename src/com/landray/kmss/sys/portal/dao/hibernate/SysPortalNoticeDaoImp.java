package com.landray.kmss.sys.portal.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.portal.dao.ISysPortalNoticeDao;
import com.landray.kmss.sys.portal.model.SysPortalNotice;
import com.landray.kmss.util.UserUtil;

/**
 * @author linxiuxian
 *
 */
public class SysPortalNoticeDaoImp extends BaseDaoImp
		implements ISysPortalNoticeDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysPortalNotice mainModel = (SysPortalNotice) modelObj;
		mainModel.setDocCreator(UserUtil.getUser());
		mainModel.setDocCreateTime(new Date());
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		SysPortalNotice mainModel = (SysPortalNotice) modelObj;
		if (mainModel.getDocCreator() == null) {
			mainModel.setDocCreator(UserUtil.getUser());
		}
		if (mainModel.getDocCreateTime() == null) {
			mainModel.setDocCreateTime(new Date());
		}
		super.update(modelObj);
	}

}
