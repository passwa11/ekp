package com.landray.kmss.sys.mportal.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import com.landray.kmss.sys.mportal.model.SysMportalCard;
import com.landray.kmss.sys.mportal.plugin.MportalMportletUtil;
import com.landray.kmss.sys.mportal.service.ISysMportalCompositeService;
import com.landray.kmss.sys.mportal.xml.MportletOperation;
import com.landray.kmss.sys.mportal.xml.SysMportalMportlet;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysMportalMportletUtil {

	@SuppressWarnings("unchecked")
	public static void loadConfigure(JSONObject cardJson, SysMportalCard card)
			throws UnsupportedEncodingException {

		if(card.getFdPortletConfig()==null) {
            return;
        }
		JSONArray configs = JSONArray.fromObject(card.getFdPortletConfig());

		for (int i = 0; i < configs.size(); i++) {
			JSONObject config = configs.getJSONObject(i);
			

			String portletId = (String) config.remove("portletId");

			SysMportalMportlet mp = MportalMportletUtil
					.getPortletById(portletId);

			if (mp == null) {
				// 部件卡片被删除
				configs.remove(i);
				i = i - 1;
				// config.put("isDel", true);
				continue;
			}

			JSONObject varObject = config.getJSONObject("vars");
			if (varObject != null && !varObject.isEmpty()) {

				Iterator<Object> objkey = varObject.keys();
				while (objkey.hasNext()) {
					String key = objkey.next().toString();
					Object val = varObject.get(key);
					if (val instanceof JSONObject) {
						varObject.put(key, varObject.getJSONObject(key)
								.getString(key));
					} else {
						varObject.put(key, URLEncoder.encode(
								varObject.getString(key), "UTF-8"));
					}
				}
			}

			JSONObject operationsObject = config.getJSONObject("operations");

			if (operationsObject != null && !operationsObject.isEmpty()) {

				Iterator<Object> objkey = operationsObject.keys();

				while (objkey.hasNext()) {

					String key = objkey.next().toString();
					Boolean val = operationsObject.getBoolean(key);
					List<MportletOperation> operations = mp.getFdOperations();

					for (MportletOperation operation : operations) {

						if (operation.getType().equals(key)) {

							if (val) {
								JSONObject obj = new JSONObject();
								String body = operation.getBody();
								if (StringUtil.isNotNull(body)) {
									obj.put("cfg", JSONObject.fromObject(body));
								}
								obj.put("href", operation.getHref());
								obj.put("name", operation.getName());
								obj.put("type", operation.getType());
								operationsObject.element(key, obj);
							}
						}

					}
				}
			}

//			config.put("module", ResourceUtil.getMessage(mp.getFdModule()));

			if (StringUtil.isNotNull(mp.getFdModuleUrl())) {
				String more = ResourceUtil.getString("sysMportalPage.more", "sys-mportal");
				config.put("more", more + "|" + mp.getFdModuleUrl());
			}

			if (StringUtil.isNotNull(mp.getJsUrl())) {
				config.put("jsUrl", mp.getJsUrl());
			} else {
				config.put("url", mp.getFdJspUrl());
			}
			
			if (StringUtil.isNotNull(mp.getCssUrl())) {
				config.put("cssUrl", mp.getCssUrl());
			}

		}
		cardJson.put("configs", configs);

	}
	
	/**
	 * 获取复合门户页面数据
	 * @param fdId
	 * @return
	 */
	public static String getCompositePagesJsonById(String fdId) {
		ISysMportalCompositeService sysMportalCompositeService = (ISysMportalCompositeService) SpringBeanUtil.getBean("sysMportalCompositeService");
		return sysMportalCompositeService.getPagesJsonById(fdId);	
	}
	
	/**
	 * 根据复合页面ID获取门户字符串
	 * @param fdId
	 * @return
	 */
	public static String getStringCompositeByPageId(String cpageId) {
		ISysMportalCompositeService sysMportalCompositeService = (ISysMportalCompositeService) SpringBeanUtil.getBean("sysMportalCompositeService");
		return sysMportalCompositeService.getStringCompositeByPageId(cpageId);	
	}
	
	
}
