package com.landray.kmss.sys.time.dao.hibernate;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataDaoImp;
import com.landray.kmss.sys.time.dao.ISysTimeHolidayDao;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.util.UserUtil;

/**
 * 节假日设置数据访问接口实现
 * 
 * @author
 * @version 1.0 2017-09-26
 */
public class SysTimeHolidayDaoImp extends ExtendDataDaoImp
		implements
			ISysTimeHolidayDao {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeHoliday holiday = (SysTimeHoliday) modelObj;
		holiday.setDocCreateTime(new Date());
		holiday.setDocCreator(UserUtil.getUser());
		return super.add(modelObj);
	}
}
