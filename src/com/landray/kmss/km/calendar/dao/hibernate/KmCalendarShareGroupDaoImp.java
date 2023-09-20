package com.landray.kmss.km.calendar.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.calendar.dao.IKmCalendarShareGroupDao;
import com.landray.kmss.km.calendar.model.KmCalendarShareGroup;
import com.landray.kmss.util.UserUtil;

/**
 * 日程群组设置 数据访问接口实现
 * 
 * @author 
 * @version 1.0 2013-10-14
 */
public class KmCalendarShareGroupDaoImp extends BaseDaoImp implements IKmCalendarShareGroupDao {
	
	@Override
    public String add(IBaseModel modelObj) throws Exception {
		KmCalendarShareGroup shareGroup = (KmCalendarShareGroup) modelObj;
		shareGroup.setDocCreator(UserUtil.getUser());
		shareGroup.setDocCreateTime(new Date());
		return super.add(modelObj);
	}

}
