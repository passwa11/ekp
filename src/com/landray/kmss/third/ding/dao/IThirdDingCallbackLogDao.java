package com.landray.kmss.third.ding.dao;

import com.landray.kmss.common.dao.IBaseDao;

public interface IThirdDingCallbackLogDao extends IBaseDao {
    public void clear(int days) throws Exception;
}
