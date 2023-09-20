<%@ page language="java" contentType="text/javascript; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="java.util.*,com.landray.kmss.util.ResourceUtil"%>
define(function(require, exports, module) {
<%
	//设置缓存
	long expires = 7 * 24 * 60 * 60;
	long nowTime = System.currentTimeMillis();
	response.addDateHeader("Last-Modified", nowTime + expires);
	response.addDateHeader("Expires", nowTime + expires * 1000);
	response.addHeader("Cache-Control", "max-age=" + expires);

	JSONObject json = new JSONObject();
	try {
		Boolean debug = ResourceUtil.isDebug();
		String bundle = request.getParameter("bundle");
		String prefix = request.getParameter("prefix");
		if(StringUtil.isNull(prefix)){
			prefix = "____all";
		}
		/**
		String resource = "";
		if (StringUtil.isNull(bundle))
			resource = ResourceUtil.APPLICATION_RESOURCE_NAME;
		else
			resource = "com.landray.kmss."
					+ bundle.replaceAll("-", ".") + "."
					+ ResourceUtil.APPLICATION_RESOURCE_NAME;
		Method method = ResourceUtil.class
				.getDeclaredMethod("getLocaleByUser");
		method.setAccessible(true);
		Locale locale = (Locale) method.invoke(ResourceUtil.class);

		ResourceBundle resourceBundle = (locale == null) ? ResourceBundle
				.getBundle(resource)
				: ResourceBundle.getBundle(resource, locale);
		if (resourceBundle != null) {
			Enumeration<String> keys = resourceBundle.getKeys();
			for (; keys.hasMoreElements();) {
				String key = keys.nextElement();
				if (!"____all".equals(prefix)
						&& !key.startsWith(prefix)) {
					continue;
				}
				if (debug) {
					json.accumulate(key, "["
							+ resourceBundle.getString(key) + "]");
				} else
					json.accumulate(key, resourceBundle.getString(key));
			}
			out.print("module.exports = " + json.toString(4) + ";");
		}
		*/
		
		if (StringUtil.isNull(bundle)) {
			bundle = "/";
		} else {
			bundle = "/" + bundle.replaceAll("-", "/") + "/";
			
		}
		// 直接从请求中获取语言参数：locale
        Locale locale = ResourceUtil.getLocale(request.getParameter("locale"));
        if (locale == null) {
            // 如果请求中的语言为空，则获取官方语言
            locale = ResourceUtil.getOfficialLang();
        }
		Map<String, String> message = com.landray.kmss.sys.profile.util.ResourceBundle.getInstance().getStringByModule(bundle, locale);
		if (message != null) {
			for (String key : message.keySet()) {
				if (!"____all".equals(prefix) && !key.startsWith(prefix)) {
					continue;
				}
				if (debug) {
					json.accumulate(key, "[" + message.get(key) + "]");
                    if(ResourceUtil.isQuicklyEdit()){
						com.alibaba.fastjson.JSONArray quicklyEditKeys = (com.alibaba.fastjson.JSONArray) request.getAttribute("quicklyEditKeys");
						if(quicklyEditKeys == null){
							quicklyEditKeys = new com.alibaba.fastjson.JSONArray();
							request.setAttribute("quicklyEditKeys", quicklyEditKeys);
						}
						com.alibaba.fastjson.JSONObject langKey = new com.alibaba.fastjson.JSONObject();
						langKey.put("bundle", bundle);
						langKey.put("key", key);
						quicklyEditKeys.add(langKey);
					}
				} else
					json.accumulate(key, message.get(key));
			}
			out.print("module.exports = " + json.toString(4) + ";");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
});
