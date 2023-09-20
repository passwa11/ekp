package com.landray.kmss.sys.time.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.time.dao.ISysTimePatchworkDao;
import com.landray.kmss.sys.time.model.SysTimePatchwork;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 补班设置数据访问接口实现
 */
public class SysTimePatchworkDaoImp extends BaseDaoImp implements
		ISysTimePatchworkDao {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimePatchwork sysTimePatchwork = (SysTimePatchwork) modelObj;
		sysTimePatchwork.setDocCreator(UserUtil.getUser());
		return super.add(sysTimePatchwork);
	}
}
