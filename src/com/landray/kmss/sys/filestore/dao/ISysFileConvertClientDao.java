package com.landray.kmss.sys.filestore.dao;

import com.landray.kmss.common.dao.IBaseDao;

public interface ISysFileConvertClientDao extends IBaseDao {

	public void updateConverterConfig(String clientId, String converterConfig);

}
