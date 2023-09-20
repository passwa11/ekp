<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil"%>
<script>
var XformObject_Lang = {};
<%
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
				if (key.startsWith("XformObject_Lang.")) {
					out.println(key + " = \""
							+ resourceBundle.getString(key).replaceAll("\"", "\\\\\"") + "\";");
				}
			}
		}

	} catch (Exception e) {
		e.printStackTrace();
		out.print("XformObject_Lang.Error = 'error'");
	}
%>
</script>
<c:set var="XformObject_Lang_Init" scope="request" value="true"/>