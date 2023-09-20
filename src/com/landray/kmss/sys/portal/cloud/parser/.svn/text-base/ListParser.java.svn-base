package com.landray.kmss.sys.portal.cloud.parser;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.landray.kmss.sys.portal.cloud.dto.PortletRequestVO;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.portal.cloud.util.PortletConstants;
import com.landray.kmss.web.util.RequestUtils;

import net.sf.json.JSONObject;

/**
 * {"help":"{messageKey，配置说明}","url":"/data/km-summary/kmSummaryPortlet/listTemplate"
 * }
 * 
 * @author chao
 *
 */
public class ListParser extends HelpParser {
	
	@Override
	public String getKind() {
		return "list";
	}

	@Override
	public void parse(JSONObject content) {
		super.parse(content);
		if (!CollectionUtils.isEmpty(content)) {
			if (content.get("url") != null) {
				String url = content.getString("url");
				PortletRequestVO vo = new PortletRequestVO();
				Map<String, Object> params = CloudPortalUtil.getUrlParams(url);
				// 分页参数
				params.put("pageno",
						PortletConstants.PAGE_NO_PLACE_HOLDER);
				params.put("rowsize",
						PortletConstants.PAGE_SIZE_PLACE_HOLDER);
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
				// id属性，与返回值有关
				if (content.get("idProperty") == null) {
					content.put("idProperty", "fdId");
				}
			}
		}
	}
}
