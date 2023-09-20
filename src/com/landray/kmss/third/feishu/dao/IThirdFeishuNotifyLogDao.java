package com.landray.kmss.third.feishu.dao;

import com.landray.kmss.common.dao.IBaseDao;

public interface IThirdFeishuNotifyLogDao extends IBaseDao {

	public void clear(int days) throws Exception;
}
