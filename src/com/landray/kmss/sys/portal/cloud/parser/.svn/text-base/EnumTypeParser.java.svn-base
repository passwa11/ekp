package com.landray.kmss.sys.portal.cloud.parser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.enums.Type;
import com.sunbor.web.tag.enums.ValueLabel;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * { "type":"enums.xml定义的枚举类型", "showType":"select/radio/checkbox",
 * "help":"{messageKey，配置说明}" }
 * <p>
 * 这里是系统定义好的枚举类型，需要将其转换为enumValue相同的格式{@link EnumValueParser}
 * </p>
 * 
 * @author chao
 *
 */
public class EnumTypeParser extends HelpParser {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EnumTypeParser.class);

	@Override
	public String getKind() {
		return "enumType";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void parse(JSONObject content) {
		super.parse(content);
		if (!CollectionUtils.isEmpty(content) && content.get("type") != null) {
			try {
				JSONArray array = new JSONArray();
				String enumType = content.getString("type");
				content.remove("type");
				Type type = EnumerationTypeUtil.newInstance().getColumnEnums()
						.findType(enumType);
				List<ValueLabel> valueLabels = type.getValueLabels();
				for (int i = 0; i < valueLabels.size(); i++) {
					ValueLabel valueLabel = valueLabels.get(i);
					String messageKey = valueLabel.getLabelKey();
					if (StringUtil.isNotNull(valueLabel.getBundle())) {
						messageKey = valueLabel.getBundle()
								+ CloudPortalUtil.MESSAGE_SPLIT + messageKey;
					}
					JSONObject value = new JSONObject();
					value.put("label",
							CloudPortalUtil.replaceMessageKey(messageKey));
					value.put("value", valueLabel.getValue());
					array.add(value);
				}
				content.put("options", array);
				// 复选框默认多选
				if (content.get("showType") != null
						&& "checkbox".equals(content.getString("showType"))
						&& content.get("multi") == null) {
					content.put("multi", true);
				}
			} catch (Exception e) {
				if (logger.isInfoEnabled()) {
					logger.info("获取枚举值出错!", e);
				}
			}
		}
	}
}
