package com.landray.kmss.sys.handover.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.sys.handover.dao.ISysHandoverConfigMainDao;
import com.landray.kmss.sys.handover.model.SysHandoverConfigMain;

/**
 * 日志主文档数据访问接口实现
 * 
 * @author
 * @version 1.0 2014-07-22
 */
public class SysHandoverConfigMainDaoImp extends BaseDaoImp implements ISysHandoverConfigMainDao {

	@Override
	public void updateState(int state, String mainId) throws Exception {
		SysHandoverConfigMain handMain = (SysHandoverConfigMain) findByPrimaryKey(mainId);
		handMain.setFdState(state);
		update(handMain);
	}

}
