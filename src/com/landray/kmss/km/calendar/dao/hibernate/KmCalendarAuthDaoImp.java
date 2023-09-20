package com.landray.kmss.km.calendar.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.calendar.dao.IKmCalendarAuthDao;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.util.UserUtil;

/**
 * 日程共享人员数据访问接口实现
 * 
 * @author 
 * @version 1.0 2013-10-14
 */
public class KmCalendarAuthDaoImp extends BaseDaoImp implements IKmCalendarAuthDao {

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		KmCalendarAuth kmCalendarAuth = (KmCalendarAuth) modelObj;
		//kmCalendarAuth.setDocCreator(UserUtil.getUser());
		kmCalendarAuth.setDocCreateTime(new Date());
		return super.add(modelObj);
	}

	@Override
    public void update(IBaseModel modelObj) throws Exception {
		KmCalendarAuth kmCalendarAuth = (KmCalendarAuth) modelObj;
		//kmCalendarAuth.setDocCreator(UserUtil.getUser());
		kmCalendarAuth.setDocCreateTime(new Date());
		super.update(modelObj);
	}

}
