package com.landray.kmss.sys.webservice2.service;

import java.util.List;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.webservice2.model.SysWebserviceDictConfig;
/**
 * 数据字典配置业务对象接口
 * 
 * @author 
 * @version 1.0 2017-12-21
 */
public interface ISysWebserviceDictConfigService extends IBaseService {

	/**
	 * 根据modelName获取数据字典配置
	 * 
	 * @param modelName
	 * @return
	 * @throws Exception
	 */
	public List<SysWebserviceDictConfig> getDictConfig(String modelName) throws Exception;
	
}
