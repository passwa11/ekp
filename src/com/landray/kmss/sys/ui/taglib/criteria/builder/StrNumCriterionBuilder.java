/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.ui.taglib.widget.BuildUtils;
import com.landray.kmss.util.StringUtil;

/**
 * @author 傅游翔
 * 
 */
public class StrNumCriterionBuilder extends AbstractCriterionBuilder implements
		CriterionBuilder {

	private static final List<String> strTypes = new ArrayList<String>();
	private static final List<String> numTypes = new ArrayList<String>();
	static {
		strTypes.add("string");
		strTypes.add("text");
		strTypes.add("rtf");
		numTypes.add("number");
		numTypes.add("int");
		numTypes.add("integer");
		numTypes.add("float");
		numTypes.add("double");
		numTypes.add("long");
		numTypes.add("bigdecimal");
		numTypes.add("biginteger");
	}

	@Override
    public boolean support(SysDictCommonProperty property) {
		if (StringUtil.isNotNull(property.getEnumType())
				|| StringUtil.isNotNull(property.getEnumValues())) {
			return false;
		}
		String type = property.getType().toLowerCase();
		return strTypes.contains(type) || numTypes.contains(type);
	}

	protected String buildSelectHtml(SysDictCommonProperty property)
			throws Exception {
		String tp = strTypes.contains(property.getType().toLowerCase()) ? "TextInput"
				: "NumberInput";
		String title = getTitle(property);
		JSONObject config = new JSONObject();
		config.put("placeholder", title);
		String selectHtml = BuildUtils.buildLUIHtml(null,
				"lui/criteria/criterion_input!" + tp, null, config, null);
		return selectHtml;
	}

	@Override
    public String build(SysDictModel model, SysDictCommonProperty property,
                        PageContext pageContext, Map<String, Object> attrs)
			throws Exception {
		String selectHtml = buildSelectHtml(property);
		String title = getTitle(property);
		JSONObject cfg = new JSONObject();
		String key = property.getName();
		cfg.put("title", title);
		cfg.put("key", key);
		cfg.put("expand", attrs.get("expand"));
		cfg.put("canMulti", attrs.get("multi"));
		return buildCriterion(cfg, selectHtml);
	}

}
