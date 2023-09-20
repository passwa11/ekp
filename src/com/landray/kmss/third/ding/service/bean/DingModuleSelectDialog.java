package com.landray.kmss.third.ding.service.bean;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.config.design.SysCfgHomePage;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.util.*;

public class DingModuleSelectDialog implements IXMLDataBean {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(DingModuleSelectDialog.class);

	@Override
    public List getDataList(RequestContext requestInfo) throws Exception {
		// 加载模块
		List rtnVal = new ArrayList();
		for (Iterator iterator = SysConfigs.getInstance()
				.getHomePages().values().iterator(); iterator.hasNext();) {
			SysCfgHomePage homePage = (SysCfgHomePage) iterator.next();

			String insertText = ResourceUtil.getString(
					homePage.getMessageKey(), requestInfo.getLocale());

			log.debug(insertText);
			if (StringUtil.isNull(insertText)) {
				continue;
			}
			Map node = new HashMap();
			String idVal = homePage.getUrlPrefix();
			node.put("id", idVal);
			node.put("name", insertText);
			rtnVal.add(node);
			// 支持多语言
//			Map<String, String> langs = SysLangUtil.getSupportedLangs();
//			for (String key : langs.keySet()) {
//				if (key.equals(SysLangUtil.getOfficialLang())) {
//					continue;
//				}
//				node.put("dynamicMap_fdName" + key + "_",
//						ResourceUtil.getStringValue(homePage.getMessageKey(), null,
//								SysLangUtil.getLocaleByShortName(key)));
//			}

		}

		//加上业务建模应用
		try {
			Object modelingUtil = Class.forName("com.landray.kmss.sys.modeling.base.util.ModelingUtil").newInstance();
			Method getAppInfo = modelingUtil.getClass().getMethod("getAppInfo");
			Object list = getAppInfo.invoke(modelingUtil);
			if(list!=null){
				List<Map<String,String>> appList= (List<Map<String,String>>) list;
				appList.forEach(map->{
					Map node = new HashMap();
					node.put("id", map.get("preUrl"));
					node.put("name", map.get("name"));
					rtnVal.add(node);
				});
			}
		} catch (Exception e) {
			log.warn("获取业务建模应用失败："+e.getMessage(),e);
		}
		return rtnVal;
	}
}
