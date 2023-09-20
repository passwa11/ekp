package com.landray.kmss.sys.zone.taglib;

import java.io.StringWriter;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.sys.ui.taglib.widget.BuildUtils;
import com.landray.kmss.sys.zone.util.SysZoneConfigUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class InfoPersonTag extends SimpleTagSupport {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(InfoPersonTag.class);

	private String device;

	public String getDevice() {
		if (StringUtil.isNull(this.device)) {
			this.setDevice("pc");
		}
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	private String personId;

	public String getPersonId() {
		if (StringUtil.isNull(personId)) {
			setPersonId(UserUtil.getUser().getFdId());
		}
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	private String infoId;

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public Boolean isDefaultTemplate = Boolean.TRUE;

	public Boolean getIsDefaultTemplate() {
		return isDefaultTemplate;
	}

	public void setIsDefaultTemplate(Boolean isDefaultTemplate) {
		this.isDefaultTemplate = isDefaultTemplate;
	}

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private Map<String, String> resourceMap = null;

	public Map<String, String> getResourceMap() {
		if (null == resourceMap || resourceMap.isEmpty()) {
			resourceMap = (Map<String, String>) this.getSource(getInfoId());
		}
		return resourceMap;
	}

	@Override
    public void doTag() throws JspException {

		if (StringUtil.isNull(getPersonId()) || (null == getResourceMap())) {
			return;
		}
		PageContext pageContext = (PageContext) getJspContext();

		try {
			pageContext.getOut().print(acquireString(pageContext));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.toString());
			throw new JspTagException(e);
		}
	}

	private String acquireString(PageContext pageContext) throws Exception {
		// 权限校验，没有该模块权限的时候不显示
		Map<String, String> tmpMap = getResourceMap();
		String sourceUrl = (String) tmpMap.get("source_url");
		if (!UserUtil.checkAuthentication(sourceUrl, "GET")) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(this.buildSource()).append(this.buildRender());
		if (this.getIsDefaultTemplate()) {// 接收事件用
			JspFragment body = getJspBody();
			StringWriter out = new StringWriter();
			body.invoke(out);
			String str = out.toString();
			sb.append(str);
		}
		return buildDataView(sb.toString());
	}

	private String buildDataView(String body) {
		String type = "lui/base!DataView";
		return BuildUtils.buildLUIHtml(null, type, null, null, body);
	}

	private String buildSource() {
		Map<String, String> tmpMap = getResourceMap();
		String type = "lui/data/source!";
		String sourceType = "AjaxJson";
		JSONObject config = new JSONObject();
		String code = null;
		config.put("params", "personId=" + this.getPersonId());
		if (null != tmpMap && !tmpMap.isEmpty()) {

			String infoId = tmpMap.get("infoId");
			int index = infoId.indexOf(SysZoneConfigUtil.SEPARATOR);
			// name://
			if (index > 0) {
				sourceType = "AjaxJsonp";
				String server = SysZoneConfigUtil.getServerUrl(infoId.substring(0, index));
				config.put("contextPath", server);
			}

			code = BuildUtils.buildCodeHtml(null, "{'url':'" + tmpMap.get("source_url") + "'}");
		}
		return BuildUtils.buildLUIHtml(null, type + sourceType, null, config, code);
	}

	private String buildRender() throws Exception {
		String klass = "lui/view/render!";
		String type = "Javascript";
		JSONObject config = new JSONObject();
		if (this.getIsDefaultTemplate()) {
			Map<String, String> tmpMap = this.getResourceMap();
			if (null != tmpMap && !tmpMap.isEmpty()) {
				String infoId = tmpMap.get("infoId");
				String renderSrc = ("pc".equals(getDevice()) ? tmpMap.get("render_url_pc")
						: tmpMap.get("render_url_mobile"));
				type = tmpMap.get("renderType");
				int index = infoId.indexOf(SysZoneConfigUtil.SEPARATOR);
				if (index > 0) {
					
					String server = SysZoneConfigUtil.getServerUrl(infoId.substring(0, index));
					renderSrc = server + renderSrc;
					config.element("dataType", "jsonp");
				}
				config.element("src", renderSrc);
			}
			return BuildUtils.buildLUIHtml(null, klass + type, null, config, null);
		} else {
			if (StringUtil.isNotNull(this.getType())) {
				type = this.getType();
			}
			JspFragment body = getJspBody();
			StringWriter out = new StringWriter();
			// 执行标签体，把内容传入流中
			body.invoke(out);
			String str = out.toString();
			return BuildUtils.buildLUIHtml(null, klass + type, null, null, BuildUtils.buildCodeHtml(null, str));
		}
	}

	private Map<String, String> getSource(String infoId) {
		Map<String, Map> maps = SysZoneConfigUtil.getOtherInfosMap();
		return maps.get(infoId);
	}

}
