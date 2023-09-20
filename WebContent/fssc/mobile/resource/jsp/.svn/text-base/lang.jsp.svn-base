<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil"%>
<%
Method method = ResourceUtil.class.getDeclaredMethod("getLocaleByUser");
method.setAccessible(true);
Locale locale = (Locale) method.invoke(ResourceUtil.class);
JSONObject json = new JSONObject();
String[] modules={"fssc-mobile","fssc-expense","fssc-fee","fssc-loan",""};
Map<String, String> message =new HashMap<String, String>();
for(int n=0,length=modules.length;n<length;n++){
	String bundle=modules[n];
	if (StringUtil.isNull(bundle)) {
		bundle = "/";
	} else {
		bundle = "/" + bundle.replaceAll("-", "/") + "/";
		
	}
	message=com.landray.kmss.sys.profile.util.ResourceBundle.getInstance().getStringByModule(bundle, locale);
	if(message!=null){
		for (String key : message.keySet()) {
			json.put((StringUtil.isNotNull(modules[n])?(modules[n]+":"):"")+key, message.get(key));
		}
	}
}
out.print("<script>window.fsscLang = " + json.toString(4) + ";</script>");
%>
