<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
var config = new Object();
<%
LinkedHashMap<String,String> langs = LoginTemplateUtil.getDesignLangs();
String[] normalKeys = new String[]{"login_logo_position","login_login_height","simplicity_logo","loginBtn_bgColor","loginBtn_bgColor_hover","loginBtn_font_color","login_form_align","title_image","login_title_show"};
String[] langKeys = new String[]{"simplicity_footerInfo","login_form_title","loginBtn_text","title"};

JSONObject designConfig = (JSONObject)request.getAttribute("config");
//Set<String> langs = SysLangUtil.getSupportedLangs().keySet();
//普通的KEY
for(String key : normalKeys) {
	if (designConfig.get(key) != null) {
		out.println("config."+key+" = '"+designConfig.get(key)+"';");
	}else if(key == "login_logo_position"){
		out.println("config."+key+" = 'logoPositionOnForm'");
	}else if(key == "login_title_show"){
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
//动态KEY
Object linksObj = designConfig.get("simplicity_head_links");
if(linksObj != null) {
	JSONArray links = (JSONArray)linksObj;
	out.print("config.simplicity_head_links = [");
	for(int i = 0;i < links.size();i++) {
		JSONObject link = links.getJSONObject(i);
		String key = "simplicity_head_link";
		out.print("{");
		for(String lang : langs.keySet()) {
			String configKey = key + "_"+lang;
			if(link.get(configKey) == null) {
				out.print(configKey+" : '',");
			}else {
				out.print(configKey+" : '"+link.getString(configKey)+"',");
			}
		}
		out.print("simplicity_head_link_href : '"+link.getString("simplicity_head_link_href")+"'}");
		if(i != links.size() - 1)
			out.print(",");
	}
	out.println("];");
}else {
	out.println("config.simplicity_head_links = [];");
}
%>
</script>