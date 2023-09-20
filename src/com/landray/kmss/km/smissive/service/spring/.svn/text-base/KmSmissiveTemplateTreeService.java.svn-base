package com.landray.kmss.km.smissive.service.spring;

import com.landray.kmss.common.service.BaseTemplateTreeService;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.km.smissive.service.IKmSmissiveTemplateService;

/**
 * @author 张鹏xn
 * 
 * 模板树菜单
 */
public class KmSmissiveTemplateTreeService extends BaseTemplateTreeService {
	private IKmSmissiveTemplateService kmSmissiveTemplateService;

	public void setKmSmissiveTemplateService(
			IKmSmissiveTemplateService kmSmissiveTemplateService) {
		this.kmSmissiveTemplateService = kmSmissiveTemplateService;
	}

	@Override
    protected IBaseService getServiceImp() {
		return kmSmissiveTemplateService;
	}

}
