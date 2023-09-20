package com.landray.kmss.km.calendar.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.calendar.dao.IKmCalendarRequestAuthDao;
import com.landray.kmss.km.calendar.model.KmCalendarRequestAuth;

public class KmCalendarRequestAuthDaoImp extends BaseDaoImp
		implements IKmCalendarRequestAuthDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmCalendarRequestAuth requestAuth = (KmCalendarRequestAuth) modelObj;
		requestAuth.setDocCreateTime(new Date());
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmCalendarRequestAuth requestAuth = (KmCalendarRequestAuth) modelObj;
		requestAuth.setDocCreateTime(new Date());
		super.update(modelObj);
	}
}
