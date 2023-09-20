package com.landray.kmss.sys.portal.cloud.parser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.landray.kmss.sys.portal.cloud.dto.PortletRequestVO;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.portal.cloud.util.PortletConstants;
import com.landray.kmss.web.util.RequestUtils;

import net.sf.json.JSONObject;
/**
 * {"model":"com.landray.kmss.km.imissive.model.KmImissiveSendTemplate","name":"{button.select}"}
 * 
 * @author chao
 *
 */
public class SysCategoryParser extends HelpParser {
	
	@Override
	public String getKind() {
		return "sys.category";
	}
	
	@Override
	public void parse(JSONObject content) {
		super.parse(content);
		if (!CollectionUtils.isEmpty(content)) {
			if (content.get("name") != null) {
				content.put("name",
						CloudPortalUtil
								.replaceMessageKey(content.getString("name")));
			}
			if (content.get("title") != null) {
				content.put("title",
						CloudPortalUtil
								.replaceMessageKey(content.getString("title")));
			}
			// /data/sys-common/treexml?s_bean=sysCategoryTreeService&showType=0&getTemplate=2&nodeType=node&authType=00&modelName=com.landray.kmss.km.imissive.model.KmImissiveSendTemplate
			PortletRequestVO vo = new PortletRequestVO();
			String modelName = content.getString("model");
			Map<String, Object> params = new HashMap<>();
			// 参数可参照/sys/category/varkind/selectcategory.jsp
			params.put("s_bean", "sysCategoryTreeService");
			params.put("modelName", modelName);
			params.put("showType", "0");
			params.put("getTemplate", "2");
			params.put("authType", "00");
			params.put("nodeType", PortletConstants.NODE_TYPE_PLACE_HOLDER);
			params.put("categoryId", PortletConstants.PARENT_PLACE_HOLDER);
			// 是否可以选择分类，区分简单分类和全局分类
			if (content.get("canSelectCategory") == null) {
				content.put("canSelectCategory", true);
			}
			params.put("searchWord", PortletConstants.SEARCH_WORD_PLACE_HOLDER);
			params.put("canSelectCategory",
					content.getString("canSelectCategory"));
			vo.setParams(params);
			vo.setUrl(CloudPortalUtil
					.addAppNameInUri(PortletConstants.TREE_DATA_SOURCE_URL));
			vo.setDataType("xml");
			content.putAll(JSONObject.fromObject(vo));
			// 系统code
			content.put("sysID", RequestUtils.getAppName());
			// id属性，与返回值有关
			content.put("idProperty", "value");
			// name属性
			content.put("nameProperty", "text");
			if (content.get("useCommonDetailsUrl") != null
					&& content.getBoolean("useCommonDetailsUrl")) {
				// 如果是使用的公共的详情URL，则将其覆盖
				String detailsUrl = "/data/sys-common/categoryDetails?fdId=!{fdId}&model="
						+ content.getString("model");
				content.put("detailsUrl",
						CloudPortalUtil.addAppNameInUri(detailsUrl));
				content.remove("useCommonDetailsUrl");
			}
		}
	}
}
