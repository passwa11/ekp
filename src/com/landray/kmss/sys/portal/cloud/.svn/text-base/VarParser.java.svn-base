package com.landray.kmss.sys.portal.cloud;

import com.landray.kmss.sys.portal.cloud.dto.PortletConfigVO;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiVar;
/**
 * 解析&lt;var&gt;标签中的多语言信息
 * 
 * @author chao
 *
 */
public class VarParser {

	public PortletConfigVO parse(SysUiVar var) {
		PortletConfigVO config = CloudPortalUtil.varToConfig(var);
		config.setLabel(CloudPortalUtil.replaceMessageKey(var.getName()));
		IVarKindParser kindParser = VarKindParserFactory.getInstance()
				.getParser(var.getKind());
		if (kindParser != null) {
			kindParser.parse(config.getContent());
		}
		return config;
	}

}
