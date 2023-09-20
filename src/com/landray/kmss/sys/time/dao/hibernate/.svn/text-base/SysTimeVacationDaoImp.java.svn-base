package com.landray.kmss.sys.time.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.time.dao.ISysTimeVacationDao;
import com.landray.kmss.sys.time.model.SysTimeVacation;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 休假设置数据访问接口实现
 */
public class SysTimeVacationDaoImp extends BaseDaoImp implements
		ISysTimeVacationDao {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeVacation sysTimeVacation = (SysTimeVacation) modelObj;
		sysTimeVacation.setDocCreator(UserUtil.getUser());
		return super.add(sysTimeVacation);
	}

}
