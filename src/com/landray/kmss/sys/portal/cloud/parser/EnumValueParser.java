package com.landray.kmss.sys.portal.cloud.parser;

import org.springframework.util.CollectionUtils;

import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * { "values":[{ "text":"{messageKey}", "value":"1" }],
 * "showType":"select/radio/checkbox", "help":"{messageKey，配置说明}" }
 * 
 * @author chao
 *
 */
public class EnumValueParser extends HelpParser {
	
	@Override
	public String getKind() {
		return "enumValue";
	}
	
	@Override
	public void parse(JSONObject content) {
		super.parse(content);
		if (!CollectionUtils.isEmpty(content)
				&& content.get("values") != null) {
			JSONArray values = content.getJSONArray("values");
			for (int i = 0; i < values.size(); i++) {
				JSONObject obj = values.getJSONObject(i);
				obj.put("label", CloudPortalUtil
						.replaceMessageKey(obj.getString("text")));
				obj.remove("text");
			}
			CloudPortalUtil.replaceKey(content, "values", "options");
			// 复选框默认多选
			if (content.get("showType") != null
					&& "checkbox".equals(content.getString("showType"))
					&& content.get("multi") == null) {
				content.put("multi", true);
			}
		}
	}
}
