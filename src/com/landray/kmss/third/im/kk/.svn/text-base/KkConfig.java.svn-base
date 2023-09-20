package com.landray.kmss.third.im.kk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.third.im.kk.model.KkImConfig;
import com.landray.kmss.third.im.kk.service.IKkImConfigService;
import com.landray.kmss.util.SpringBeanUtil;

public class KkConfig {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KkConfig.class);

	private static Map<String, String> kkConifgCache = new HashMap<String, String>();

	public KkConfig() {
		try {
			if (null == getDataMap() || getDataMap().size() <= 0) {
				Map<String, String> map = new HashMap<String, String>();
				IKkImConfigService kkImConfigService = (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");
				List<KkImConfig> list = kkImConfigService.findList(new HQLInfo());
				if (null != list && list.size() > 0) {
					for (KkImConfig config : list) {
						map.put(config.getFdKey(), config.getFdValue());
					}
					getDataMap().putAll(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getValue(String name) {
		return (String) getDataMap().get(name);
	}

	public void setValue(String name, String value) {
		getDataMap().put(name, value);
	}

	public Map<String, String> getDataMap() {
		return kkConifgCache;
	}

}
