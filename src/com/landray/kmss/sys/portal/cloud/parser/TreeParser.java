package com.landray.kmss.sys.portal.cloud.parser;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.landray.kmss.sys.portal.cloud.dto.PortletRequestVO;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.web.util.RequestUtils;

import net.sf.json.JSONObject;

/**
 * {"help":"{messageKey，配置说明}","url":"/data/sys-common/treexml?s_bean=kmForumCategoryTeeService&categoryId=!{parentId}&fromPrtlet=true"
 * }
 * 
 * @author chao
 *
 */
public class TreeParser extends HelpParser {
	
	@Override
	public String getKind() {
		return "tree";
	}

	@Override
	public void parse(JSONObject content) {
		super.parse(content);
		if (!CollectionUtils.isEmpty(content)) {
			if (content.get("url") != null) {
				String url = content.getString("url");
				PortletRequestVO vo = new PortletRequestVO();
				Map<String, Object> params = CloudPortalUtil.getUrlParams(url);
				String dataType = "xml";
				if (content.get("dataType") != null) {
					dataType = content.getString("dataType");
				}
				vo.setDataType(dataType);
				vo.setParams(params);
				int index = url.indexOf("?");
				if (index > -1) {
					url = url.substring(0, index);
				}
				vo.setUrl(CloudPortalUtil.addAppNameInUri(url));
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
