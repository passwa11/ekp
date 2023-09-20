package com.landray.kmss.third.weixin.work.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSource;

public class Wx2ekpPersonCustomData implements ICustomizeDataSource {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Wx2ekpPersonCustomData.class);

	@Override
	public Map<String, String> getOptions() {
		Map<String, String> returnMap = new HashMap<String, String>();
		// 基本信息 姓名、Userid、登录名、手机号、部门、职务、性别、邮箱、座机、别名、open_userid、地址、主部门
		returnMap.put("name",
				ResourceUtil.getString("wx2ekp.org.config.name",
						"third-weixin-work"));
		returnMap.put("userid",
				ResourceUtil.getString("wx2ekp.org.config.userId",
						"third-weixin-work"));
		returnMap.put("mobile",
				ResourceUtil.getString("wx2ekp.org.config.mobile",
						"third-weixin-work"));
		returnMap.put("mainDept",
				ResourceUtil.getString("wx2ekp.org.config.mainDept",
						"third-weixin-work"));
		returnMap.put("position",
				ResourceUtil.getString("wx2ekp.org.config.position",
						"third-weixin-work"));
		returnMap.put("email",
				ResourceUtil.getString("wx2ekp.org.config.email",
						"third-weixin-work"));
		returnMap.put("telephone",
				ResourceUtil.getString("wx2ekp.org.config.tel",
						"third-weixin-work"));
		returnMap.put("alias", ResourceUtil
				.getString("wx2ekp.org.config.alias", "third-weixin-work"));
		returnMap.put("address", ResourceUtil
				.getString("wx2ekp.org.config.address", "third-weixin-work"));
		returnMap.put("open_userid", ResourceUtil
				.getString("wx2ekp.org.config.open_userid",
						"third-weixin-work"));
		returnMap.put("gender",
				ResourceUtil.getString("wx2ekp.org.config.sex",
						"third-weixin-work"));
		returnMap.put("order",
				ResourceUtil.getString("wx2ekp.org.config.order",
						"third-weixin-work"));
		return returnMap;
	}

	@Override
	public String getDefaultValue() {
		return null;
	}

}
