package com.landray.kmss.sys.time.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.time.dao.ISysTimeWorkDao;
import com.landray.kmss.sys.time.model.SysTimeWork;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 班次设置数据访问接口实现
 */
public class SysTimeWorkDaoImp extends BaseDaoImp implements ISysTimeWorkDao {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeWork sysTimeWork = (SysTimeWork) modelObj;
		sysTimeWork.setDocCreator(UserUtil.getUser());
		return super.add(sysTimeWork);
	}
}
