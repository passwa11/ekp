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
String[] normalKeys = new String[]{"single_logo","login_logo_position","single_login_bg","loginBtn_bgColor","loginBtn_bgColor_hover","loginBtn_font_color","login_form_align","login_form_background_color","title_image","login_title_show"};
String[] langKeys = new String[]{"single_footerInfo","single_logo_text","loginBtn_text","login_form_title","title"};
JSONObject designConfig = (JSONObject)request.getAttribute("config");
//普通的KEY
for(String key : normalKeys) {
	if (designConfig.get(key) != null) {
	 	out.println("config."+key+" = '"+designConfig.getString(key)+"';");
	} else if (key == "login_logo_position") {
	 	out.println("config."+key+" = 'logoPositionOnForm'");
	} else if (key == "login_title_show") {
	 	out.println("config."+key+" = 'loginTitleShow'");
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