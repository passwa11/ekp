package com.landray.kmss.sys.time.dao.hibernate;

import com.landray.kmss.common.dao.BaseDaoImp;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.time.dao.ISysTimeAreaDao;
import com.landray.kmss.sys.time.model.SysTimeArea;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 区域组设置数据访问接口实现
 */
public class SysTimeAreaDaoImp extends BaseDaoImp implements ISysTimeAreaDao {
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeArea sysTimeArea = (SysTimeArea) modelObj;
		sysTimeArea.setDocCreator(UserUtil.getUser());
		new KmssCache(SysTimeArea.class).clear();
		return super.add(sysTimeArea);
	}

	/**
	 * 清除缓存
	 */
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		super.update(modelObj);
		new KmssCache(SysTimeArea.class).clear();
	}

	/**
	 * 清除缓存
	 */
	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		super.delete(modelObj);
		new KmssCache(SysTimeArea.class).clear();
	}
}
