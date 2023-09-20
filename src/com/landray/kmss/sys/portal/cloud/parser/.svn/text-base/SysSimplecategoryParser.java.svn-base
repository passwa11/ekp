package com.landray.kmss.sys.portal.cloud.parser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.landray.kmss.sys.portal.cloud.IVarKindParser;
import com.landray.kmss.sys.portal.cloud.dto.PortletRequestVO;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.portal.cloud.util.PortletConstants;
import com.landray.kmss.web.util.RequestUtils;

import net.sf.json.JSONObject;

/**
 * {"model":"com.landray.kmss.sys.news.model.SysNewsTemplate","name":"{sys-news:sysNewsMain.portlet.selectCategory}"}
 * 
 * @author chao
 *
 */
public class SysSimplecategoryParser extends HelpParser
		implements IVarKindParser {
	
	@Override
	public String getKind() {
		return "sys.simplecategory";
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
			// /data/sys-common/treexml?s_bean=sysSimpleCategoryTreeService&authType=00&modelName=com.landray.kmss.km.books.model.KmBooksCategory
			PortletRequestVO vo = new PortletRequestVO();
			String modelName = content.getString("model");
			Map<String, Object> params = new HashMap<>();
			// 参数可参照/sys/simplecategory/varkind/selectsimplecategory.jsp
			params.put("s_bean", "sysSimpleCategoryTreeService");
			params.put("modelName", modelName);
			params.put("authType", "00");
			params.put("categoryId", PortletConstants.PARENT_PLACE_HOLDER);
			params.put("keyword", PortletConstants.SEARCH_WORD_PLACE_HOLDER);
			vo.setParams(params);
			vo.setUrl(CloudPortalUtil
					.addAppNameInUri(PortletConstants.TREE_DATA_SOURCE_URL));
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
