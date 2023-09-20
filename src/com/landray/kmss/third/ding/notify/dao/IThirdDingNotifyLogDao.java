package com.landray.kmss.third.ding.notify.dao;

import com.landray.kmss.common.dao.IBaseDao;

public interface IThirdDingNotifyLogDao extends IBaseDao {

	public void clear(int days) throws Exception;
}
