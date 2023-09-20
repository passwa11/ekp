package com.landray.kmss.sys.portal.cloud.parser;

import org.springframework.util.CollectionUtils;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.portal.cloud.IVarKindParser;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.sso.client.oracle.StringUtil;

import net.sf.json.JSONObject;
public abstract class HelpParser implements IVarKindParser {

	@Override
	public void parse(JSONObject content) {
		if (!CollectionUtils.isEmpty(content)) {
			if (content.get("help") != null) {
				content.put("help", CloudPortalUtil
						.replaceMessageKey(content.getString("help")));
			}
			if (content.get("suffix") != null) {
				content.put("suffix", CloudPortalUtil
						.replaceMessageKey(content.getString("suffix")));
			}
			if (content.get("prefix") != null) {
				content.put("prefix", CloudPortalUtil
						.replaceMessageKey(content.getString("prefix")));
			}
			if(content.get("model") != null) {
				if (content.get("detailsUrl") == null) {
					// 使用公共的详情url
					content.put("useCommonDetailsUrl", true);
					content.put("detailsUrl", "/data/sys-common/details?fdId=!{fdId}&model="
										+ content.getString("model"));
				}
				// name属性，与获取详情有关
				String nameKey = "fdName";
				SysDictModel dict = SysDataDict.getInstance()
						.getModel(content.getString("model"));
				if (StringUtil.isNotNull(dict.getDisplayProperty())) {
					nameKey = dict.getDisplayProperty();
				}
				if (content.get("displayProperty") == null) {
					content.put("displayProperty", nameKey);
				}
				if (content.get("nameProperty") == null) {
					content.put("nameProperty", nameKey);
				}
			}
			if (content.get("detailsUrl") != null) {
				content.put("detailsUrl", CloudPortalUtil
						.addAppNameInUri(content.getString("detailsUrl")));
			}
		}
	}
}
