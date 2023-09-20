package com.landray.kmss.sys.ui.taglib.widget;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;

@SuppressWarnings("serial")
public abstract class BaseTag extends BodyTagSupport implements TryCatchFinally {

	public static final String CONFIG_PREFIX = "config-";

	public static final String CONFIG_SHORT_PREFIX = "cfg-";

	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(BaseTag.class);

	@Override
	public String getId() {
		if (StringUtil.isNull(this.id)) {
			if (StringUtil.isNotNull(getIdKey())) {
				this.id = "lui_" + getIdKey() + "_" + IDGenerator.generateID();
			}
		}
		return this.id;
	}

	protected String getIdKey() {
		return "";
	}

	protected void registerToParent() {
		BaseTag parent = ((BaseTag) findAncestorWithClass(this, BaseTag.class));
		if (parent != null) {
			parent.receiveSubTaglib(this);
		}
	}

	protected void receiveSubTaglib(BodyTagSupport taglib) {
		// 供子类覆盖实现
	}

	public String getContentPath() {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		return request.getContextPath();
	}

	public String contentPath(String url) {
		if (!url.startsWith("/")) {
			url = "/" + url;
		}
		return getContentPath() + url;
	}

	@Override
    public void doCatch(Throwable t) throws Throwable {
		logger.error(getClass().getName() + ":error:", t);
		throw t;
	}

	@Override
    public void doFinally() {
		try {
			release();
		} catch (Exception e) {
			logger.error(getClass().getName() + ":release:", e);
		}
	}

	@Override
	public void release() {
		id = null;
		config = new HashMap<String, String>();
		super.release();
	}

	protected Map<String, String> config = new HashMap<String, String>();

	protected void addConfig(String key, String value) {
		if (key.startsWith(CONFIG_PREFIX)) {
			key = key.substring(CONFIG_PREFIX.length());

		} else if (key.startsWith(CONFIG_SHORT_PREFIX)) {
			key = key.substring(CONFIG_SHORT_PREFIX.length());
		}
		config.put(key, value);
	}

	protected JSONObject buildConfigJson() {
		JSONObject cfg = new JSONObject();
		cfg.putAll(config);
		postBuildConfigJson(cfg);
		return cfg;
	}

	protected void postBuildConfigJson(JSONObject cfg) {
		// 供子类覆盖使用
	}

}
