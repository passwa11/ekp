/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.widget.ref;

import java.util.Map;

import javax.servlet.jsp.PageContext;

import net.sf.json.JSONObject;

/**
 * 变量保存辅组类
 * 
 * @author 傅游翔
 * 
 */
public abstract class AttributeUtil {

	public static void set(PageContext pageContext, String name, Object value) {
		Object old = pageContext.getAttribute(name, PageContext.REQUEST_SCOPE);
		pageContext.setAttribute(name, value, PageContext.REQUEST_SCOPE);
		pageContext.setAttribute("__old__" + name, old,
				PageContext.REQUEST_SCOPE);
	}

	public static void release(PageContext pageContext, String name) {
		Object old = pageContext.getAttribute("__old__" + name,
				PageContext.REQUEST_SCOPE);
		pageContext.setAttribute(name, old, PageContext.REQUEST_SCOPE);
	}

	public static void putAttrsToConfig(PageContext pageContext, String name,
			JSONObject cfg) {
		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) pageContext
				.getAttribute(name, PageContext.REQUEST_SCOPE);
		if (params != null) {
            cfg.putAll(params);
        }
	}
}
