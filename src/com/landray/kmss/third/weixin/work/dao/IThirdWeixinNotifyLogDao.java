package com.landray.kmss.third.weixin.work.dao;

import com.landray.kmss.common.dao.IBaseDao;

public interface IThirdWeixinNotifyLogDao extends IBaseDao {

	public void clear(int days) throws Exception;
}
