/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.ui.taglib.widget.BuildUtils;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.enums.Type;
import com.sunbor.web.tag.enums.ValueLabel;

/**
 * @author 傅游翔
 * 
 */
public class EnumCriterionBuilder extends AbstractCriterionBuilder implements
		CriterionBuilder {

	@Override
	public boolean support(SysDictCommonProperty property) {
		return (StringUtil.isNotNull(property.getEnumType()));
	}

	protected JSONArray buildDatas(SysDictCommonProperty property)
			throws Exception {
		Type type = EnumerationTypeUtil.newInstance().getColumnEnums()
				.findType(property.getEnumType());
		JSONArray datas = new JSONArray();
		for (Iterator<?> it = type.getValueLabels().iterator(); it.hasNext();) {
			ValueLabel valueLabel = (ValueLabel) it.next();
			String value = valueLabel.getValue();
			String bundle = valueLabel.getBundle();
			if (StringUtil.isNull(bundle)) {
				bundle = type.getBundle();
			}
			String label = ResourceUtil.getString(valueLabel.getLabelKey(),
					bundle);

			JSONObject obj = new JSONObject();
			obj.put("text", label);
			obj.put("value", value);
			datas.add(obj);
		}
		return datas;
	}

	protected String buildSelectHtml(SysDictCommonProperty property)
			throws Exception {
		JSONArray datas = buildDatas(property);
		String html = BuildUtils.buildLUIHtml(null, "lui/data/source!Static",
				null, null, BuildUtils.buildCodeHtml(null, datas.toString()));
		String selectHtml = BuildUtils.buildLUIHtml(null,
				"lui/criteria!CriterionSelectDatas", null, null, html);
		return selectHtml;
	}

	@Override
	public String build(SysDictModel model, SysDictCommonProperty property,
						PageContext pageContext, Map<String, Object> attrs)
			throws Exception {
		String selectHtml = buildSelectHtml(property);

		JSONObject cfg = new JSONObject();
		String title = getTitle(property);
		String key = property.getName();
		cfg.put("title", title);
		cfg.put("key", key);
		cfg.put("expand", attrs.get("expand"));
		// Boolean类型不需要多选
		if ("Boolean".equals(property.getType())) {
            cfg.put("canMulti", Boolean.FALSE);
        } else {
            cfg.put("canMulti", attrs.get("multi"));
        }
		return buildCriterion(cfg, selectHtml);
	}

}
