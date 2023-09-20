package com.landray.kmss.third.weixin.mutil.dao;

import java.util.Map;

import com.landray.kmss.common.dao.IBaseDao;

public interface IThirdWxWorkConfigDao extends IBaseDao {

	/**
	 * <p>保存配置</p>
	 * @param key
	 * @param map
	 * @throws Exception
	 * @author 孙佳
	 */
	public void save(String key, Map fieldValues) throws Exception;

}
