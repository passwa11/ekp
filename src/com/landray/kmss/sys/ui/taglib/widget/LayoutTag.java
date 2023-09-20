package com.landray.kmss.sys.ui.taglib.widget;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.util.Assert;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiLayout;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.filter.ResourceCacheFilter;

@SuppressWarnings("serial")
public class LayoutTag extends WidgetTag {
	@Override
    public String getType() {
		if (StringUtil.isNotNull(getRef())) {
			SysUiLayout layout = SysUiPluginUtil.getLayoutById(this.getRef());
			if (layout == null) {
				throw new NullPointerException("部件" + getId() + "的ref属性值："
						+ getRef() + ",没有被找到");
			} else {
				this.type = layout.getFdType();
			}
		} else {
			if (StringUtil.isNotNull(this.type)) {
				if (this.type.indexOf("!") < 0) {
					this.type = "lui/view/layout!" + this.type;
				}
			} else {
				throw new NullPointerException("部件" + getId()
						+ "的ref属性值或type属性值必填一个");
			}
		}
		return this.type;
	}

	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		if (StringUtil.isNotNull(getRef())) {
			SysUiLayout layout = SysUiPluginUtil.getLayoutById(this.getRef());
			if (!layout.getFdBody().getParam().isEmpty()) {
				cfg.put("param", layout.getFdBody().getParam());
			}
			cfg.put("kind", layout.getFdKind());
		}
		super.postBuildConfigJson(cfg);
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNotNull(getRef())) {
			SysUiLayout layout = SysUiPluginUtil.getLayoutById(this.getRef());
			String src = "";
			Assert.notNull(layout, "layout:" + this.getRef() + "没有注册");
			if (StringUtil.isNotNull(layout.getFdBody().getSrc())) {
				src = contentPath(layout.getFdBody().getSrc());
				if (src.indexOf("s_cache=") < 0) {
					if (src.indexOf("?") >= 0) {
						src = src + "&s_cache=" + ResourceCacheFilter.cache;
					} else {
						src = src + "?s_cache=" + ResourceCacheFilter.cache;
					}
				}
			} else {
				src = contentPath("/sys/ui/resources/layout.jsp?code="
						+ layout.getFdId());
			}
			sb.append(buildCodeUrlHtml(src));
		} else {
			if (StringUtil.isNotNull(body)) {
				sb.append(buildCodeScriptHtml(body));
			}
		}

		return super.acquireString(sb.toString());
	}

	public static String buildLayoutHtml(BaseTag tag, String layoutId) {
		SysUiLayout layout = SysUiPluginUtil.getLayoutById(layoutId);
		String src = "";
		Assert.notNull(layout, "layout:" + layoutId + "没有注册");
		if (StringUtil.isNotNull(layout.getFdBody().getSrc())) {
			src = (layout.getFdBody().getSrc());
			if (src.indexOf("s_cache=") < 0) {
				if (src.indexOf("?") >= 0) {
					src = src + "&s_cache=" + ResourceCacheFilter.cache;
				} else {
					src = src + "?s_cache=" + ResourceCacheFilter.cache;
				}
			}
		} else {
			src = ("/sys/ui/resources/layout.jsp?code=" + layout.getFdId());
		}
		if (!src.startsWith("/")) {
			src = "/" + src;
		}
		//src = tag.contentPath(src);
		String code = BuildUtils.buildCodeHtml(src, null);
		JSONObject config = new JSONObject();
		config.put("kind", layout.getFdKind());
		if (StringUtil.isNotNull(layout.getFdCss())) {
			config.put("css", tag.contentPath(layout.getFdCss()));
		}
		if (!layout.getFdBody().getParam().isEmpty()) {
			config.put("param", layout.getFdBody().getParam());
		}
		return BuildUtils.buildLUIHtml(null, layout.getFdType(), null, config,
				code);
	}

	public static JSONObject getLayoutConfig(ServletRequest request,
			String layoutId) throws RuntimeException {
		JSONObject layoutJson = new JSONObject();
		JSONObject layoutConfig = new JSONObject();
		SysUiLayout layout = SysUiPluginUtil.getLayoutById(layoutId);
		if (layout == null) {
			throw new NullPointerException("layout:" + layoutId + "在系统里面没有被注册");
		}
		String src = "";
		if (StringUtil.isNotNull(layout.getFdBody().getSrc())) {
			src = layout.getFdBody().getSrc();
		} else {
			src = ("/sys/ui/resources/layout.jsp?code=" + layout.getFdId());
		}
		if (!src.startsWith("/")) {
			src = "/" + src;
		}
		//src = ((HttpServletRequest) request).getContextPath() + src;
		layoutConfig.put("src", src);
		if (StringUtil.isNotNull(layout.getFdCss())) {
			layoutConfig.put(
					"css",
					((HttpServletRequest) request).getContextPath()
							+ layout.getFdCss());
		}
		layoutConfig.put("kind", layout.getFdKind());
		if (!layout.getFdBody().getParam().isEmpty()) {
			layoutConfig.put("param", layout.getFdBody().getParam());
		}
		layoutJson.put("xtype", layout.getFdType());
		layoutJson.put("config", layoutConfig);
		return layoutJson;
	}
}
