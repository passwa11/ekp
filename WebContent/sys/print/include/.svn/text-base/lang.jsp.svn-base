<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
DesignerPrint_Lang = {
};

<%
	//设置缓存
	long expires = 7 * 24 * 60 * 60;
	long nowTime = System.currentTimeMillis();
	response.addDateHeader("Last-Modified", nowTime + expires);
	response.addDateHeader("Expires", nowTime + expires * 1000);
	response.addHeader("Cache-Control", "max-age=" + expires); 

	try {
		String bundle = request.getParameter("bundle");
		String resource = "com.landray.kmss.sys.print."
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
				if (key.startsWith("DesignerPrint_Lang.")) {
					out.println(key + " = '"
							+ resourceBundle.getString(key) + "';");
				}
			}
		}

	} catch (Exception e) {
		e.printStackTrace();
		out.print("DesignerPrint_Lang.Error = 'error'");
	}
%>
DesignerPrint_Lang.GetMessage = function(msg, param1, param2, param3){
	var re;
	if(param1!=null){
		re = /\{0\}/gi;
		msg = msg.replace(re, param1);
	}
	if(param2!=null){
		re = /\{1\}/gi;
		msg = msg.replace(re, param2);
	}
	if(param3!=null){
		re = /\{2\}/gi;
		msg = msg.replace(re, param3);
	}
	return msg;
};