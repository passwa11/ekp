package com.landray.kmss.sys.ui.taglib.widget;

import com.landray.kmss.util.StringUtil;

@SuppressWarnings("serial")
public class TransformTag extends WidgetTag {

	@Override
    public String getType() {
		if (StringUtil.isNull(this.type)) {
			this.type = "lui/data/dataformat!DefaultTransform";
		} else {
			if (this.type.indexOf("!") < 0) {
				this.type = "lui/data/dataformat!" + this.type;
			}
		}
		return type;
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(buildCodeScriptHtml(body));
		return super.acquireString(sb.toString());
	}

}
