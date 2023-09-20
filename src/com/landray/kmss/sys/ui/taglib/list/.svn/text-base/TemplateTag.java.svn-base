package com.landray.kmss.sys.ui.taglib.list;
 
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.taglib.widget.RenderTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.util.StringUtil;

public class TemplateTag extends WidgetTag {

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNotNull(getRef())) {
			SysUiRender render = SysUiPluginUtil.getRenderById(getRef());
			sb.append(RenderTag.buildRenderHtml(this, render.getFdId()));
		}
		if (StringUtil.isNotNull(body)) {
			sb.append(buildCodeScriptHtml(body));
		}
		return super.acquireString(sb.toString());
	}

	@Override
    public String getType() {
		if (this.type == null) {
			return "lui/listview/template!Template";
		}
		return this.type;
	}

}
