package com.landray.kmss.third.ekp.java.notify.dao;

import com.landray.kmss.common.dao.IBaseDao;

public interface IThirdEkpJavaNotifyMappDao extends IBaseDao {

    public void cleanFinishedNotifyMapp(int days) throws Exception;
}
