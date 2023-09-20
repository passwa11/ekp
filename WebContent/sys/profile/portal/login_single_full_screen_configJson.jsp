<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var config = new Object();
<%
LinkedHashMap<String,String> langs = LoginTemplateUtil.getDesignLangs();
String[] normalKeys = new String[]{"login_logo_position","single_full_screen_logo","login_form_align","login_form_background_color","title_image"};
String[] langKeys = new String[]{"single_full_screen_footerInfo","loginBtn_text","title"};

JSONObject designConfig = (JSONObject)request.getAttribute("config");
//Set<String> langs = SysLangUtil.getSupportedLangs().keySet();
//普通的KEY
for(String key : normalKeys) {
	if (designConfig.get(key) != null) {
	  out.println("config."+key+" = '"+designConfig.getString(key)+"';");
	} else {
	  if (key == "login_logo_position") {
	    out.println("config."+key+" = 'logoPositionOnLogoShow'");
	  }
	}
}
//多语言的KEY
for(String key : langKeys) {
	for(String lang : langs.keySet()) {
		String configKey = key + "_"+lang;
		if(designConfig.get(configKey) == null) {
			out.println("config."+configKey+" = '';");
		}else {
			out.println("config."+configKey+" = '"+designConfig.getString(configKey)+"';");
		}
	}
}
%>
</script>