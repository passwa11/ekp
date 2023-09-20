/**
 * 
 */
package com.landray.kmss.sys.zone.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseCreateInfoDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.zone.dao.ISysZoneNavigationDao;
import com.landray.kmss.sys.zone.model.SysZoneNavigation;
import com.landray.kmss.util.UserUtil;

/**
 * @author 傅游翔
 */
public class SysZoneNavigationDaoImp extends BaseCreateInfoDaoImp implements
		ISysZoneNavigationDao {

	private void setAlterInfo(IBaseModel modelObj) {
		SysZoneNavigation infoModel = (SysZoneNavigation) modelObj;
		if (infoModel.getDocAlteror() == null) {
			infoModel.setDocAlteror(UserUtil.getUser());
		}
		if (infoModel.getDocAlterTime() == null) {
			infoModel.setDocAlterTime(new Date());
		}
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		setAlterInfo(modelObj);
		super.update(modelObj);
	}
}
