package com.landray.kmss.sys.transport.dao;

import java.util.List;

import com.landray.kmss.common.dao.IBaseDao;

public interface ISysTransportExportDao extends IBaseDao
{
	public List getAllByModelName(String modelName) throws Exception;
}
