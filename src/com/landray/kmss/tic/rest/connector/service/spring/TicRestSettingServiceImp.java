package com.landray.kmss.tic.rest.connector.service.spring;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.tic.rest.connector.service.ITicRestSettingService;

/**
 * REST服务配置业务接口实现
 */
public class TicRestSettingServiceImp extends BaseServiceImp implements
		ITicRestSettingService {
	private ITicRestSettingService TicRestSettingService;
	
	public void setTicRestSettingService(
			ITicRestSettingService TicRestSettingService) {
		this.TicRestSettingService = TicRestSettingService;
	}
}
