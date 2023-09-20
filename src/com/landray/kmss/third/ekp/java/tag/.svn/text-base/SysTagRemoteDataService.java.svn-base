package com.landray.kmss.third.ekp.java.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.tag.service.ISysTagDataMixService;
import com.landray.kmss.third.ekp.java.EkpJavaConfig;
import com.landray.kmss.third.ekp.java.tag.client.TagGetResult;
import com.landray.kmss.third.ekp.java.tag.client.TagGetTagsContext;
import com.landray.kmss.third.ekp.java.tag.client.TagResult;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysTagRemoteDataService implements ISysTagDataMixService {

	private ISysTagWebServiceClient sysTagWebServiceClient;

	public void setSysTagWebServiceClient(
			ISysTagWebServiceClient sysTagWebServiceClient) {
		this.sysTagWebServiceClient = sysTagWebServiceClient;
	}

	@Override
	public List<Map<String, Object>> getTags(RequestContext requestContext)
			throws Exception {

		TagGetTagsContext context = new TagGetTagsContext();
		String key = requestContext.getParameter("key");
		String paramId = requestContext.getParameter("fdCategoryId");
		String type = requestContext.getParameter("type");
		String loginName = UserUtil.getUser().getFdLoginName();

		context.setKey(key);
		context.setParamId(paramId);
		context.setType(type);
		context.setLoginName(loginName);

		return parseResult2map(sysTagWebServiceClient.getTags(context));

	}

	@Override
	public List<Map<String, Object>> getCategories(RequestContext requestContext)
			throws Exception {

		return parseResult2map(sysTagWebServiceClient
				.getCategories(requestContext.getParameter("type")));

	}

	private List<Map<String, Object>> parseResult2map(TagGetResult result) {

		List<String> datas = result.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (String data : datas) {

			Map<String, Object> map = new HashMap<String, Object>();

			json2map(JSONObject.fromObject(data), map);

			list.add(map);
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	private void json2map(JSONObject obj, Map<String, Object> map) {

		for (Iterator<String> keyStr = obj.keys(); keyStr.hasNext();) {

			String key = keyStr.next();
			map.put(key, obj.get(key));

		}

	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean enable() throws Exception {

		EkpJavaConfig config = new EkpJavaConfig();
		String enable = config.getValue("kmss.tag.java.enabled");

		if ("true".equals(enable)) {
            return true;
        }

		return false;
	}

	@Override
	public List<Map<String, Object>> getGroups(String modelName)
			throws Exception {
		TagResult result = sysTagWebServiceClient.getGroups(modelName);
		JSONArray arr = JSONArray.fromObject(result.getMessage());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Object data : arr) {
			Map<String, Object> map = new HashMap<String, Object>();
			json2map(JSONObject.fromObject(data), map);
			list.add(map);
		}
		return list;
	}

	@Override
	public JSONArray getIsSpecialByTags(List<String> tags) throws Exception {
		TagResult result = sysTagWebServiceClient.getIsSpecialByTags(tags);
		JSONArray array = JSONArray.fromObject(result.getMessage());
		return array;
	}

	@Override
	public List<Map<String, Object>>
			getTagListbyBeginTime(Map<String, Object> args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
