package com.landray.kmss.third.weixin.work.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.property.custom.DynamicAttributeConfig;
import com.landray.kmss.sys.property.custom.DynamicAttributeField;
import com.landray.kmss.sys.property.custom.DynamicAttributeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.taglib.xform.ICustomizeDataSource;

public class WxworkPersonCustomData implements ICustomizeDataSource {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(WxworkPersonCustomData.class);

	@Override
	public Map<String, String> getOptions() {
		Map<String, String> returnMap = new HashMap<String, String>();

		// 人员基本信息
		SysDictModel model = SysDataDict.getInstance()
				.getModel(
						"com.landray.kmss.sys.organization.model.SysOrgPerson");
		Map<String, SysDictCommonProperty> map = model.getPropertyMap();
		SysDictCommonProperty property;
		Set<String> proSet = map.keySet();
		String k;
		String name;
		String n;
		for (String key : proSet) {
			property = map.get(key);
			if ("fdOrgEmail".equals(property.getName())) {
                continue;
            }
			name = property.getMessageKey();
			if (StringUtil.isNotNull(name) && name.contains(":")) {
				// n = DingUtil.getValueByLang(name.split(":")[1],
				// name.split(":")[0],
				// null);

				n = ResourceUtil.getStringValue(name.split(":")[1],
						name.split(":")[0],
						ResourceUtil.getLocaleByUser());
				returnMap.put(property.getName(), n);
			}
		}

		// 自定义
		DynamicAttributeConfig config = DynamicAttributeUtil
				.getDynamicAttributeConfig(
						"com.landray.kmss.sys.organization.model.SysOrgPerson");
		if (config != null) {
			List<DynamicAttributeField> list = new ArrayList<DynamicAttributeField>(
					config.getFields());

			for (DynamicAttributeField file : list) {
				if ("false".equals(file.getStatus())) {
					continue;
				}

				String text = "";
				String officialLang = ResourceUtil
						.getKmssConfigString("kmss.lang.official");
				try {
					if (StringUtil.isNull(officialLang)) {
						text = file.getFieldText("def");
						if (StringUtil.isNull(text)) {
							text = file.getFieldText("CN");
						}
					} else {

						Locale locale = ResourceUtil.getLocaleByUser();
						String lang = locale.getCountry();
						text = file.getFieldText(lang);

						if (StringUtil.isNull(text)) {
							String defLang = UserUtil.getUser()
									.getFdDefaultLang();
							if (StringUtil.isNotNull(defLang)
									&& defLang.contains("-")) {
								text = file.getFieldText(defLang.split("-")[1]);
							}
							if (StringUtil.isNull(text)) {
								if (StringUtil.isNotNull(officialLang)
										&& officialLang.contains("-")) {
									text = file
											.getFieldText(
													officialLang.split("-")[1]);
								}
							}

							}

					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				returnMap.put(file.getFieldName(), text);
			}
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
