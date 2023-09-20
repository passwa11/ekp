package com.landray.kmss.third.pda.service.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.design.SysCfgHomePage;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.third.pda.loader.PdaSysConfig;
import com.landray.kmss.third.pda.loader.PdaSysConfigs;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class PdaModuleSelectDialog implements IXMLDataBean {
	private boolean allowAllModuleFlag = false;

	private String allowModules;

	public void setAllowAllModuleFlag(boolean allowAllModuleFlag) {
		this.allowAllModuleFlag = allowAllModuleFlag;
	}

	public void setAllowModules(String allowModules) {
		this.allowModules = allowModules;
	}

	public void setAnonymousPaths(String anonymousPaths) {
		String[] paths;
		if(StringUtil.isNotNull(anonymousPaths)){
			paths = anonymousPaths.trim().split("\\s*[,;]\\s*");
			PdaFlagUtil.CONST_PDASORUCE = paths;
		}
	}

	public boolean isAllowAllModuleFlag() {
		return allowAllModuleFlag;
	}

	public String getAllowModules() {
		return allowModules;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		// 加载模块
		List<String> enabledModuleList = new ArrayList<String>();
		if (!allowAllModuleFlag) {
			if (StringUtil.isNotNull(allowModules)) {
				String[] enabledModuleArray = allowModules
						.split("\\s*[,;]\\s*");
				if (enabledModuleArray != null) {
                    enabledModuleList = Arrays.asList(enabledModuleArray);
                }
			}
		}
		List rtnVal = new ArrayList();
		outloop: for (Iterator iterator = SysConfigs.getInstance()
				.getHomePages().values().iterator(); iterator.hasNext();) {
			SysCfgHomePage homePage = (SysCfgHomePage) iterator.next();
			// 如果不启用所有应用模块PDA功能，则只加载配置里面的模块
			if (!allowAllModuleFlag
					&& !enabledModuleList.contains(homePage.getUrlPrefix())) {
                continue;
            }
			String insertText = ResourceUtil.getString(
					homePage.getMessageKey(), requestInfo.getLocale());
			if (insertText == null) {
				if (StringUtil.isNotNull(homePage.getName())) {
					insertText = homePage.getName();
				} else {
					continue;
				}
			}
			Map node = new HashMap();
			String idVal = homePage.getUrlPrefix();
			node.put("id", idVal);
			node.put("name", insertText);
			// 支持多语言
			Map<String, String> langs = SysLangUtil.getSupportedLangs();
			for (String key : langs.keySet()) {
				if (key.equals(SysLangUtil.getOfficialLang())) {
					continue;
				}
				node.put("dynamicMap_fdName" + key + "_",
						ResourceUtil.getStringValue(homePage.getMessageKey(), null,
								SysLangUtil.getLocaleByShortName(key)));
			}
			PdaSysConfig pdaConfig = (PdaSysConfig) (PdaSysConfigs
					.getInstance().getModuleCfg(idVal));
			if (pdaConfig != null) {
                node.put("countUrl", pdaConfig.getCountUrl());
            }
			for (int i = 0; i < rtnVal.size(); i++) {
				String text = (String) ((Map) rtnVal.get(i)).get("name");
				if (text.compareTo(insertText) > 0) {
					rtnVal.add(i, node);
					continue outloop;
				}
			}
			rtnVal.add(node);
		}
		return rtnVal;
	}
}
