package com.landray.kmss.sys.ui.taglib.chart;

import com.landray.kmss.sys.ui.taglib.widget.BuildUtils;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Iterator;

public class ChartTag extends WidgetTag {
	protected String width;

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	protected String height;
	protected String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
    public String getType() {
		if (StringUtil.isNull(this.type)) {
			this.type = "lui/chart/chart!Chart";
		}
		return this.type;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			String echarts = null;
			if(StringUtil.isNull(getVersion())){
				echarts = "echarts5.3.2.js";
			}else if(getVersion().startsWith("4")){
				echarts = "echarts4.2.1.js";
			}else if(getVersion().startsWith("5")){
				echarts = "echarts5.3.2.js";
			}else{
				echarts = "echarts5.3.2.js";
			}
			pageContext
					.getOut()
					.append(
							"<script>Com_IncludeFile('"+ echarts +"','"
									+ ((HttpServletRequest) pageContext
											.getRequest()).getContextPath()
									+ "/sys/ui/js/chart/echarts/','js',true);</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_BUFFERED;
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append(StringUtil.isNull(body) ? "" : body);

		return buildLUIHtml(buildConfigJson(), sb.toString(), getType(),
				getParentId());

	}

	public String buildLUIHtml(JSONObject config, String body, String type, String parent) {
		try {
			if(config != null && config.has("vars") && config.getJSONObject("vars").has("loadMapJs")
					&& StringUtil.isNotNull(config.getJSONObject("vars").getString("loadMapJs"))){
				String loadMapJs = config.getJSONObject("vars").getString("loadMapJs");
				JSONArray loadMapJsFiles = JSONArray.fromObject(loadMapJs);
				for (Iterator<String> iterator = loadMapJsFiles.iterator(); iterator.hasNext();) {
					String loadMapJsFile = iterator.next();
					pageContext.getOut().append(
							"<script>Com_IncludeFile('" + loadMapJsFile + "','"
							+ ((HttpServletRequest) pageContext.getRequest()).getContextPath()
							+ "/sys/ui/js/chart/echarts/map/','js',true);</script>");
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		StringBuilder out = new StringBuilder();
		out.append("<div");
		if (StringUtil.isNotNull(id)) {
			out.append(" id=\"").append(id).append("\"");
		}
		String width = "600px";
		if (StringUtil.isNotNull(this.width)) {
			if (this.width.matches("^[0-9]*$")) {
				width = this.width + "px";
			} else {
				width = this.width;
			}
		}
		String height = "400px";
		if (StringUtil.isNotNull(this.height)) {
			if (this.height.matches("^[0-9]*$")) {
				height = this.height + "px";
			} else {
				height = this.height;
			}
		}
		out.append(" style=\"min-width:").append(width).append(";min-height:").append(height).append("\"");
		if (StringUtil.isNotNull(className)) {
			out.append(" class=\"").append(className).append("\"");
		}
		if (StringUtil.isNotNull(type)) {
			out.append(" data-lui-type=\"").append(type).append("\"");
		}
		if (StringUtil.isNotNull(attr)) {
			out.append(" data-lui-attr=\"").append(getAttr()).append("\"");
		}
		if (StringUtil.isNotNull(parent)) {
			out.append(" data-lui-parentid=\"").append(parent).append("\"");
		}
		out.append(" style=\"display:none;\">\t\n");
		out.append(BuildUtils.buildConfigHtml(config));
		if (StringUtil.isNotNull(body)) {
			out.append(body);
		}
		out.append("<div class = \"div_chart\"");
		out.append(" style=\"width:").append(width).append(";height:").append(height).append("\"");
		out.append("></div><div class = \"div_listSection\"");
		out.append(" style=\"overflow:auto;width:").append(width).append(";display:").append("none;height:").append(height).append(";\"");
		out.append("></div>");
		out.append("</div>");
		String result = out.toString();
		return result;
	}

	@Override
    public void release() {
		this.width = null;
		this.height = null;
		this.className = null;
		this.version = null;
		super.release();
	}

	@Override
    protected void receiveSubTaglib(BodyTagSupport taglib) {

		super.receiveSubTaglib(taglib);
	}
}
