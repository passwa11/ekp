package com.landray.kmss.sys.webservice2.service.spring;
import java.util.List;

import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.webservice2.model.SysWebserviceDictConfig;
import com.landray.kmss.sys.webservice2.service.ISysWebserviceDictConfigService;
/**
 * 数据字典配置业务接口实现
 * 
 * @author 
 * @version 1.0 2017-12-21
 */
public class SysWebserviceDictConfigServiceImp extends ExtendDataServiceImp implements ISysWebserviceDictConfigService {
	
	@Override
    public List<SysWebserviceDictConfig> getDictConfig(String modelName) throws Exception {
		String sql = "sysWebserviceDictConfig.fdModelName = '" + modelName + "'";

		return this.findList(sql, null);
	}

}
