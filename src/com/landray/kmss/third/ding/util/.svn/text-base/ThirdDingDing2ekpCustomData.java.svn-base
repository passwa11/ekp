package com.landray.kmss.third.ding.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingDing2ekpCustomData implements ICustomizeDataSource {

	static String custom = null;

	public static void getDingCustomInfo() {
		try {
			custom = DingUtils.getDingApiService().getDingCustomInfo();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingDing2ekpCustomData.class);
	@Override
	public Map<String, String> getOptions() {
		Map<String, String> returnMap = new HashMap<String, String>();
		// 基本信息
		returnMap.put("name",
				ResourceUtil.getString("enums.ding2ekp.name", "third-ding"));
		returnMap.put("userid",
				ResourceUtil.getString("enums.ding2ekp.userId", "third-ding"));
		returnMap.put("mobile",
				ResourceUtil.getString("enums.ding2ekp.mobile", "third-ding"));
		returnMap.put("department",
				ResourceUtil.getString("enums.ding2ekp.dept", "third-ding"));
		returnMap.put("order",
				ResourceUtil.getString("enums.ding2ekp.order", "third-ding"));
		returnMap.put("email",
				ResourceUtil.getString("enums.ding2ekp.email", "third-ding"));
		returnMap.put("orgEmail", ResourceUtil
				.getString("enums.ding2ekp.orgEmail", "third-ding"));
		returnMap.put("position", ResourceUtil
				.getString("enums.ding2ekp.position", "third-ding"));
		returnMap.put("tel",
				ResourceUtil.getString("enums.ding2ekp.tel", "third-ding"));
		returnMap.put("jobnumber", ResourceUtil
				.getString("enums.ding2ekp.jobnumber", "third-ding"));
		returnMap.put("workPlace", ResourceUtil
				.getString("enums.ding2ekp.workAddress", "third-ding"));
		returnMap.put("hiredDate", ResourceUtil
				.getString("enums.ding2ekp.hiredDate", "third-ding"));
		returnMap.put("remark",
				ResourceUtil.getString("enums.ding2ekp.remark", "third-ding"));


		// 获取钉钉自定义字段
		try {

			if (StringUtil.isNotNull(custom)) {
				JSONObject rs = JSONObject.fromObject(custom);
				JSONObject property = new JSONObject();
				if (rs.getInt("errcode") == 0 && rs.containsKey("result")) {
					JSONArray ja = rs.getJSONArray("result");
					if (ja != null && ja.size() > 0
							&& ja.getJSONObject(0).containsKey("field_list")) {
						JSONArray field_list = ja.getJSONObject(0)
								.getJSONArray("field_list");
						if (field_list != null && field_list.size() > 0) {
							for (int i = 0; i < field_list.size(); i++) {
								property = field_list.getJSONObject(i);
								String name = property.getString("field_name");
								logger.debug("钉钉自定义数据：" + name);
								returnMap.put(name, name);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("获取钉钉拓展字段发生异常：" + e);
		}

		// 过滤value为空的数据
		if (returnMap != null && !returnMap.isEmpty()) {

			Set<String> set = returnMap.keySet();
			Iterator<String> iterator = set.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				// logger.debug("key:" + key + " value:" + returnMap.get(key));
				if (StringUtil.isNull(returnMap.get(key))) {
					logger.debug("vaule为空，去掉该key:" + key);
					iterator.remove();
					returnMap.remove(key);
				}
			}
		}

		return returnMap;
	}

	@Override
	public String getDefaultValue() {
		return null;
	}

}
