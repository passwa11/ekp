package com.landray.kmss.third.ekp.java.notify.dao;

import com.landray.kmss.common.dao.IBaseDao;

public interface IThirdEkpJavaNotifyQueErrDao extends IBaseDao {

	public void clear(int days) throws Exception;
}
