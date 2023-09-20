<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
var config = new Object();
<%
LinkedHashMap<String,String> langs = LoginTemplateUtil.getDesignLangs();
String[] normalKeys = new String[]{"login_logo_position","single_logo","single_login_bg","loginBtn_font_color","login_form_align","title_image","login_title_show","single_im_icon","single_kms_icon","single_ekp_icon"};
String[] langKeys = new String[]{"single_footerInfo","single_logo_text","login_form_title","loginBtn_text","title","single_im","single_kms","single_ekp"};

JSONObject designConfig = (JSONObject)request.getAttribute("config");
//普通的KEY
for(String key : normalKeys) {
	if (designConfig.get(key) != null) {
	  	out.println("config."+key+" = '"+designConfig.getString(key)+"';");
	} else if (key == "login_logo_position") {
	    out.println("config."+key+" = 'logoPositionOnLogoShow'");
	} else if (key == "login_title_show") {
		out.println("config."+key+" = 'loginTitleShow'");
	}else if(key == "single_im_icon"){
		out.println("config."+key+" = 'icon_KK.png'");
	}else if(key == "single_im_CN"){
		out.println("config."+key+" = '企业移动社交平台'");
	}else if(key == "single_kms_icon"){
		out.println("config."+key+" = 'icon_KMS.png'");
	}else if(key == "single_kms_CN"){
		out.println("config."+key+" = 'KMS知识管理平台'");
	}else if(key == "single_ekp_icon"){
		out.println("config."+key+" = 'icon_EKP.png'");
	}else if(key == "single_ekp_CN"){
		out.println("config."+key+" = 'EKP协同办公平台'");
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