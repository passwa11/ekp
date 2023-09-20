package com.landray.kmss.sys.ui.taglib.widget;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.enums.Type;
import com.sunbor.web.tag.enums.ValueLabel;

@SuppressWarnings("serial")
public class EnumSourceTag extends WidgetTag {

	@Override
    public String getType() {
		return this.type = "lui/data/source!Static";
	}

	protected String enumType = null;

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	protected String keyName = null;

	protected String valueName = null;

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		Type type = EnumerationTypeUtil.newInstance().getColumnEnums()
				.findType(enumType);
		JSONArray enumsArray = new JSONArray();
		String keyName = StringUtil.isNotNull(this.keyName) ? this.keyName
				: "key";
		String valueName = StringUtil.isNotNull(this.valueName) ? this.valueName
				: "value";
		for (int i = 0; i < type.getValueLabels().size(); i++) {
			JSONObject enums = new JSONObject();
			ValueLabel valueLabel = (ValueLabel) type.getValueLabels().get(i);
			String bundle = valueLabel.getBundle();
			if (StringUtil.isNull(bundle)) {
				bundle = type.getBundle();
			}
			// 名称
			String label = ResourceUtil.getString(valueLabel.getLabelKey(),
					bundle);
			// 值
			String value = valueLabel.getValue();
			enums.element(keyName, label);
			enums.element(valueName, value);
			enumsArray.add(enums);
		}
		sb.append(buildCodeScriptHtml(enumsArray.toString()));
		return super.acquireString(sb.toString());
	}
}
