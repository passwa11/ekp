package com.landray.kmss.sys.zone.service.spring;

import java.util.Map;

import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.zone.actions.SysZonePageTemplateAction;
import com.landray.kmss.sys.zone.service.ISysZonePageTemplateService;
import com.landray.kmss.sys.zone.util.SysZoneTemplate;
import com.landray.kmss.util.StringUtil;

public class SysZonePageTemplateServiceImp implements
		ISysZonePageTemplateService {

	private ISysAppConfigService sysAppConfigService;

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getTemplateJspPath(String type) throws Exception {
		String jspPath = SysZoneTemplate.getPageTemplateJsp(type);
		if (StringUtil.isNotNull(jspPath)) {
			return jspPath;
		}
		Map<String, String> map = sysAppConfigService.findByKey(SysZonePageTemplateAction.class.getName());
		jspPath = map.get(type);
		if (StringUtil.isNull(jspPath)) {
			jspPath = SysZoneTemplate.getDefaultPageTemplateJsp(type);
		}
		return jspPath;
	}

}
