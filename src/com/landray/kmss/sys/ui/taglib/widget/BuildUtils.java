/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.widget;

import net.sf.json.JSONObject;

import com.landray.kmss.util.StringUtil;

/**
 * 构建HTML辅组类
 * 
 * @author 傅游翔
 * 
 */
public abstract class BuildUtils {

	public static String buildLUIHtml(String id, String type, String attr,
			JSONObject config, String body, String parent) {
		StringBuilder out = new StringBuilder();
		out.append("<div");
		if (StringUtil.isNotNull(id)) {
			out.append(" id=\"").append(id).append("\"");
		}
		if (StringUtil.isNotNull(type)) {
			out.append(" data-lui-type=\"").append(type).append("\"");
		}
		if (StringUtil.isNotNull(attr)) {
			out.append(" data-lui-attr=\"").append(attr).append("\"");
		}
		if (StringUtil.isNotNull(parent)) {
			out.append(" data-lui-parentid=\"").append(parent).append("\"");
		}
		out.append(" style=\"display:none;\">\t\n");
		out.append(buildConfigHtml(config));
		if (StringUtil.isNotNull(body)) {
			out.append(body);
		}
		out.append("</div>");
		return out.toString();
	}

	public static String buildLUIHtml(String id, String type, String attr,
			JSONObject config, String body) {
		return buildLUIHtml(id, type, attr, config, body, null);
	}

	public static String buildConfigHtml(JSONObject config) {
		StringBuilder html = new StringBuilder();
		if (config != null && !config.isEmpty()) {
			html.append("<script type=\"text/config\">\r\n");
			html.append(config.toString(4));
			html.append("\r\n</script>\r\n");
		}
		return html.toString();
	}

	public static String buildCodeHtml(String src, String code) {
		StringBuilder out = new StringBuilder();
		if (StringUtil.isNotNull(src) || StringUtil.isNotNull(code)) {
			out.append("<script type='text/code'");
			if (StringUtil.isNotNull(src)) {
				out.append(" xsrc='" + src + "'");
			}
			out.append(">");
			if (StringUtil.isNotNull(code)) {
				out.append("\r\n");
				out.append(code.trim());
			}
			out.append("\r\n</script>\r\n");
		}
		return out.toString();
	}

}
