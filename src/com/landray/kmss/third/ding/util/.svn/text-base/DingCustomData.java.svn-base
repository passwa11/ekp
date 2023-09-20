package com.landray.kmss.third.ding.util;

import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DingCustomData implements ICustomizeDataSource {

	private static final Logger logger = LoggerFactory.getLogger(DingCustomData.class);
	@Override
	public Map<String, String> getOptions() {
		Map<String, String> returnMap = new HashMap<String, String>();
		// 获取钉钉自定义字段
		try {

			if (StringUtil.isNotNull(ThirdDingDing2ekpCustomData.custom)) {
				JSONObject rs = JSONObject.fromObject(ThirdDingDing2ekpCustomData.custom);
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
