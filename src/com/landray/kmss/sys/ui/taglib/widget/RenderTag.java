package com.landray.kmss.sys.ui.taglib.widget;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.filter.ResourceCacheFilter;

@SuppressWarnings("serial")
public class RenderTag extends WidgetTag {

	protected String css;

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	@Override
    public void release() {
		this.css = null;
		super.release();
	}

	@Override
    public String getType() {
		if (StringUtil.isNotNull(getRef())) {
			SysUiRender render = SysUiPluginUtil.getRenderById(this.getRef());
			if (render == null) {
				throw new NullPointerException("部件" + getId() + "的ref属性值："
						+ getRef() + ",没有被找到");
			} else {
				this.type = render.getFdType();
			}
		} else {
			if (StringUtil.isNull(this.type)) {
				throw new NullPointerException("render部件" + getId()
						+ "的ref属性值或type属性值必填一个");
			} else {
				if (this.type.indexOf("!") < 0) {
					this.type = "lui/view/render!" + this.type;
				}
			}
		}
		return type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		if (StringUtil.isNotNull(getRef())) {
			SysUiRender render = SysUiPluginUtil.getRenderById(this.getRef());
			if (!render.getFdBody().getParam().isEmpty()) {
				cfg.put("param", render.getFdBody().getParam());
			}
			String fdCss = render.getFdCss();
			if (StringUtil.isNotNull(fdCss)) {
				if (fdCss.startsWith("/")) {
					fdCss = fdCss.substring(1);
				}
				cfg.put("css", fdCss);
			}
		}
		super.postBuildConfigJson(cfg);
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNotNull(getRef())) {
			SysUiRender render = SysUiPluginUtil.getRenderById(getRef());
			String src = "";
			if (StringUtil.isNotNull(render.getFdBody().getSrc())) {
				src = render.getFdBody().getSrc();
				if(src.indexOf("s_cache=")<0){
					if(src.indexOf("?")>=0){
						src = src + "&s_cache=" + ResourceCacheFilter.cache;
					}else{
						src = src + "?s_cache=" + ResourceCacheFilter.cache;
					}
				}
			} else {
				src = "/sys/ui/resources/render.jsp?code=" + render.getFdId();
			}
			if(!src.startsWith("/")){
				src = "/" + src;
			}
			//src = contentPath(src);
			sb.append(buildCodeUrlHtml(src));
		} else {
			if (StringUtil.isNotNull(body)) {
				sb.append(buildCodeScriptHtml(body));
			}
		}

		return super.acquireString(sb.toString());
	}

	public static String buildRenderHtml(BaseTag tag, String renderId) {
		SysUiRender render = SysUiPluginUtil.getRenderById(renderId);
		String src = "";
		if (StringUtil.isNotNull(render.getFdBody().getSrc())) {
			src = render.getFdBody().getSrc();
		} else {
			src = "/sys/ui/resources/render.jsp?code=" + render.getFdId();
		}
		
		String code = BuildUtils.buildCodeHtml(src, null);
		JSONObject config = new JSONObject();
		if (StringUtil.isNotNull(render.getFdCss())) {
			config.put("css", render.getFdCss());
		}
		// todo var
		if (!render.getFdBody().getParam().isEmpty()) {
			config.put("param", render.getFdBody().getParam());
		}
		return BuildUtils.buildLUIHtml(null, render.getFdType(), null, config,
				code);
	}
}
