package com.landray.kmss.third.ding.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.third.ding.model.DingConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ThirdDingDing2ekpRoleCustomData implements ICustomizeDataSource {

	static String custom = null;

	public static void getDingCustomInfo() {
		try {
			String dingEnabled = DingConfig.newInstance().getDingEnabled();
			if(!"true".equals(dingEnabled)){
				return;
			}
			custom = DingUtils.getDingApiService().getRoleList();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdDingDing2ekpRoleCustomData.class);
	@Override
	public Map<String, String> getOptions() {
		Map<String, String> returnMap = new HashMap<String, String>();
		// 获取角色
		try {
			logger.warn("钉钉角色列表：" + custom);
			if (StringUtil.isNotNull(custom)) {
				JSONObject rs = JSONObject.fromObject(custom);
				if (rs.getInt("errcode") == 0 && rs.containsKey("result")) {
					JSONObject result = rs.getJSONObject("result");
					JSONArray list = result.getJSONArray("list");
					for (int i = 0; i < list.size(); i++) {
						JSONObject role = list.getJSONObject(i);
						// 加上特殊分隔符 名称+id
						returnMap.put(role.getString("groupId"),
								role.getString("name"));
					}

				}
			}

		} catch (Exception e) {
			logger.error("获取角色发生异常：" + e);
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
