package com.landray.kmss.km.archives.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.design.SysCfgHomePage;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class KmArchivesModuleSelectService implements IXMLDataBean {

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List rtnVal = new ArrayList();
		for (Iterator iterator = SysConfigs.getInstance()
				.getHomePages().values().iterator(); iterator.hasNext();) {
			SysCfgHomePage homePage = (SysCfgHomePage) iterator.next();
			String insertText = ResourceUtil.getString(
					homePage.getMessageKey(), requestInfo.getLocale());
			if (insertText == null) {
                continue;
            }
			Map node = new HashMap();
			String idVal = homePage.getUrlPrefix();
			node.put("id", idVal);
			node.put("name", insertText);
			rtnVal.add(node);
		}
		return rtnVal;
	}

	public String getModuleNames(String fdModules) {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isNull(fdModules)) {
			return "";
		}
		String[] modules = fdModules.split("[;；]");
		for (String module : modules) {
			if (!module.startsWith("/")) {
                module = "/" + module;
            }
			if (!module.endsWith("/")) {
                module += "/";
            }
			SysCfgModule cfg = SysConfigs.getInstance().getModule(module);
			if (cfg != null) {
                sb.append(ResourceUtil.getString(cfg.getMessageKey()) + ";");
            }
		}
		if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
		return sb.toString();
	}
}
