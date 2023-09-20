package com.landray.kmss.km.calendar.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.calendar.dao.IKmCalendarPersonGroupDao;
import com.landray.kmss.km.calendar.model.KmCalendarPersonGroup;
import com.landray.kmss.util.UserUtil;

public class KmCalendarPersonGroupDaoImp extends BaseDaoImp
		implements IKmCalendarPersonGroupDao {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmCalendarPersonGroup model = (KmCalendarPersonGroup) modelObj;
		if (model.getDocCreator() == null) {
            model.setDocCreator(UserUtil.getUser());
        }
		if (model.getDocCreateTime() == null) {
            model.setDocCreateTime(new Date());
        }
		return super.add(modelObj);
	}

}
