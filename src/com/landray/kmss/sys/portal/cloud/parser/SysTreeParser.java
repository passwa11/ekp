package com.landray.kmss.sys.portal.cloud.parser;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.landray.kmss.sys.portal.cloud.dto.PortletRequestVO;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.portal.cloud.util.PortletConstants;
import com.landray.kmss.web.util.RequestUtils;

import net.sf.json.JSONObject;

/**
 * { "help":"{messageKey，配置说明}", "bean" :
 * "sysBookmarkCategoryTreeService&parentId=!{value}&type=all" }
 * 
 * @author chao
 *
 */
public class SysTreeParser extends HelpParser {
	
	@Override
	public String getKind() {
		return "sys.tree";
	}

	@Override
	public void parse(JSONObject content) {
		super.parse(content);
		if (!CollectionUtils.isEmpty(content)) {
			if (content.get("bean") != null) {
				PortletRequestVO vo = new PortletRequestVO();
				String url = content.getString("bean");
				Map<String, Object> params = CloudPortalUtil.getUrlParams(url);
				params.put("parentId", PortletConstants.PARENT_PLACE_HOLDER);
				vo.setParams(params);
				vo.setUrl(CloudPortalUtil.addAppNameInUri(
						PortletConstants.TREE_DATA_SOURCE_URL));
				vo.setDataType("xml");
				content.putAll(JSONObject.fromObject(vo));
				// 系统code
				content.put("sysID", RequestUtils.getAppName());
				// 是否可以选择分类，区分简单分类和全局分类
				if (content.get("canSelectCategory") == null) {
					content.put("canSelectCategory", true);
				}
				// id属性，与返回值有关
				content.put("idProperty", "value");
				// name属性
				content.put("nameProperty", "text");
			}
		}
	}
}
