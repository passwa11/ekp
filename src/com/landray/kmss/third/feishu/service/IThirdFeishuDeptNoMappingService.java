package com.landray.kmss.third.feishu.service;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;

public interface IThirdFeishuDeptNoMappingService extends IExtendDataService {

	public void addNoMapping(String feishuId, String feishuName,
			String feishuPath) throws Exception;

	public void deleteFeishu(String fdId, String feishuId) throws Exception;

	public boolean updateEKP(String fdId, String fdEKPId) throws Exception;
}
