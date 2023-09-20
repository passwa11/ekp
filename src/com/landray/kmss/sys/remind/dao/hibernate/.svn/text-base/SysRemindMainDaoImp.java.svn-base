package com.landray.kmss.sys.remind.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.remind.dao.ISysRemindMainDao;
import com.landray.kmss.sys.remind.model.SysRemindMain;
import com.landray.kmss.util.UserUtil;

/**
 * 提醒中心
 * 
 * @author panyh
 * @date Jun 23, 2020
 */
public class SysRemindMainDaoImp extends BaseDaoImp implements ISysRemindMainDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysRemindMain model = (SysRemindMain) modelObj;
		if (model.getDocCreateTime() == null) {
			model.setDocCreateTime(new Date());
		}
		if (model.getDocCreator() == null) {
			model.setDocCreator(UserUtil.getUser());
		}
		return super.add(model);
	}

}
