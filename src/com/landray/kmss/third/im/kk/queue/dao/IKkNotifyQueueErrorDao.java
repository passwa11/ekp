package com.landray.kmss.third.im.kk.queue.dao;

import com.landray.kmss.common.dao.IBaseDao;

public interface IKkNotifyQueueErrorDao extends IBaseDao {

	public void clear(int days) throws Exception;

}
