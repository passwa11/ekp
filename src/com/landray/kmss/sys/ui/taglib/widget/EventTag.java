/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.widget;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * 事件标签
 * 
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class EventTag extends BaseTag {

	private String event;

	private String topic;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	private String forWidget;

	public String getForWidget() {
		return forWidget;
	}

	public void setForWidget(String forWidget) {
		this.forWidget = forWidget;
	}

	private String args;

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuilder sb = new StringBuilder();
		if (event != null) {
			sb.append("<script type=\"lui/event\" data-lui-event=\"")
					.append(event).append("\"");
		} else {
			sb.append("<script type=\"lui/topic\" data-lui-topic=\"")
					.append(topic).append("\"");
		}
		if (args != null) {
			sb.append(" data-lui-args=\"").append(args).append("\"");
		}
		if (forWidget != null) {
			sb.append(" data-lui-for=\"").append(forWidget).append("\"");
		}
		sb.append(">");
		if (getBodyContent() != null) {
			sb.append(getBodyContent().getString());
		}
		sb.append("</script>");

		try {
			pageContext.getOut().append(sb);
		} catch (IOException e) {
			throw new JspTagException("ui event build error:", e);
		}
		return super.doEndTag();
	}

	@Override
	public void release() {
		super.release();
		this.event = null;
		this.topic = null;
		this.forWidget = null;
		this.args = null;
	}
}
