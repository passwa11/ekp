<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
Designer_Lang = {
};
<%
	//设置缓存
	long xformExpires = 7 * 24 * 60 * 60;
	long xformNowTime = System.currentTimeMillis();
	response.addDateHeader("Last-Modified", xformNowTime + xformExpires);
	response.addDateHeader("Expires", xformNowTime + xformExpires * 1000);
	response.addHeader("Cache-Control", "max-age=" + xformExpires); 

	try {
		String bundle = request.getParameter("bundle");
		String resource = "com.landray.kmss.sys.xform.base."
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
				if (key.startsWith("Designer_Lang.")) {
					out.println(key + " = '"
							+ resourceBundle.getString(key) + "';");
				}
			}
		}
		//兼容低代码平台业务关联控件多语言
		String _printKey = (String)pageContext.getAttribute("_printKey");
		if("modelingApp".equals(_printKey)){
			out.println("Designer_Lang.placeholder = '" + ResourceUtil.getString("sys-modeling-base:xform.placeholder.title",locale) +"';");
		}

	} catch (Exception e) {
		e.printStackTrace();
		out.print("Designer_Lang.Error = 'error'");
	}
%>
Designer_Lang.GetMessage = function(msg, param1, param2, param3){
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