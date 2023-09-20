package com.landray.kmss.sys.ui.taglib.widget;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.SysUiConstant;
import com.landray.kmss.sys.ui.xml.model.SysUiSource;
import com.landray.kmss.util.StringUtil;

@SuppressWarnings("serial")
public class SourceTag extends WidgetTag {

	@Override
    public String getType() {
		if (StringUtil.isNotNull(getRef())) {
			SysUiSource source = SysUiPluginUtil.getSourceById(this.getRef());
			if (source == null) {
				throw new NullPointerException("部件" + this.getId() + "的ref属性值："
						+ this.getRef() + ",没有被找到");
			} else {
				String _type = source.getFdType();
				if(source.getFdId().indexOf(SysUiConstant.SEPARATOR)>0){
					_type = _type.replaceAll("AjaxJson", "AjaxJsonp"); 
					_type = _type.replaceAll("AjaxXml", "AjaxJsonp"); 
					_type = _type.replaceAll("AjaxText", "AjaxJsonpText"); 
				} 
				this.type = _type;
			}
		} else {
			if (StringUtil.isNull(this.type)) {
				throw new NullPointerException("source部件" + getId()
						+ "的ref属性值或type属性值必填一个");
			} else {
				if (this.type.indexOf("!") < 0) {
					this.type = "lui/data/source!" + this.type;
				}
			}
		}
		return type;
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();

		if (StringUtil.isNotNull(getRef())) {
			SysUiSource source = SysUiPluginUtil.getSourceById(this.getRef());
			if (source == null) {
				throw new NullPointerException("source:" + this.getRef()
						+ "没有注册");
			}
			if (StringUtil.isNotNull(source.getFdBody().getSrc())) {
				sb.append(buildCodeUrlHtml(contentPath(source.getFdBody()
						.getSrc())));
			} else {
				sb.append(buildCodeScriptHtml(source.getFdBody().getBody()));
			}
		} else {
			sb.append(buildCodeScriptHtml(body));
		}

		return super.acquireString(sb.toString());
	}

	public static String buildSourceHtml(BaseTag tag, String sourceId,JSONObject cfg) {
		SysUiSource source = SysUiPluginUtil.getSourceById(sourceId);
		JSONObject config = new JSONObject();
		if (cfg!=null && !cfg.isEmpty()) {
			config.putAll(cfg);
		}
		// todo var
		String code = BuildUtils.buildCodeHtml(source.getFdBody().getSrc(),
				source.getFdBody().getBody());
		return BuildUtils.buildLUIHtml(null, source.getFdType(), null, config,
				code);
	}
}
