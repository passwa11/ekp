package com.landray.kmss.sys.ui.taglib.widget.container;

import javax.servlet.jsp.tagext.BodyTagSupport;

import com.landray.kmss.sys.ui.taglib.widget.LayoutTag;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class CalendarTag extends WidgetTag {
	private String layout;
	
	private String mode;
	
	private String showStatus;
	
	private String customMode;

	public String getLayout() {
		if (StringUtil.isNull(this.layout)) {
			if (this.type == null) {
				this.layout = "sys.ui.calendar.default";
			} else if ("lui/rescalendar!ResCalendar".equals(this.type)) {
				this.layout = "sys.ui.rescalendar.default";
			}
		}
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Override
    public String getType() {
		if (this.type == null) {
			this.type = "lui/calendar!Calendar";
		}
		return this.type;
	}

	public String getMode() {
		if (this.mode == null) {
			this.mode = "default";
		}
		return this.mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getShowStatus() {
		if (this.showStatus == null) {
			this.showStatus = "view";
		}
		return this.showStatus;
	}

	public void setShowStatus(String showStatus) {
		this.showStatus = showStatus;
	}

	public String getCustomMode() {
		return customMode;
	}

	public void setCustomMode(String customMode) {
		this.customMode = customMode;
	}

	@Override
    protected String acquireString(String body) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (!hasLayout) {
			sb.append(LayoutTag.buildLayoutHtml(this, this.getLayout()));
		}
		sb.append(body == null ? "" : body);
		return super.acquireString(sb.toString());
	}

	protected boolean hasLayout = false;

	@Override
    protected void receiveSubTaglib(BodyTagSupport taglib) {
		if (taglib instanceof LayoutTag) {
			hasLayout = true;
		}
		super.receiveSubTaglib(taglib);
	}

	@Override
    public void release() {
		this.layout = null;
		super.release();
	}
	
	@Override
    protected void postBuildConfigJson(JSONObject cfg) {
		putConfigValue(cfg, "mode", this.getMode());
		putConfigValue(cfg, "showStatus", this.getShowStatus());
		putConfigValue(cfg, "customMode", this.getCustomMode());
		super.postBuildConfigJson(cfg);
	}
}
