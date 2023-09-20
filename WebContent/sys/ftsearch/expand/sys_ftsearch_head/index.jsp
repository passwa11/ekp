<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<!DOCTYPE html>

<html>
<%
    String locale = UserUtil.getKMSSUser(request).getLocale().toString();
    
	if(locale == null || locale.trim().length() == 0){
		locale = "zh_CN";
	}
 %>
 
	<head>
		<meta charset="UTF-8">
		<title>elasticsearch-head</title>
		<link rel="stylesheet" href="base/reset.css">
		<link rel="stylesheet" href="vendor.css">
		<link rel="stylesheet" href="app.css">
		<script>
			var lang = "<%=locale%>";
		</script>
		<script src="i18n.js" data-baseDir="lang" data-langs="en,fr,pt,zh,zh-TW,tr,ja"></script>
		<script src="vendor.js"></script>
		<script src="app.js"></script>
		<script>
			window.onload = function() {
				i18n.setLocale(lang);
				if(location.href.contains("/_plugin/")) {
					var base_uri = location.href.replace(/_plugin\/.*/, '');
				}
				var args = location.search.substring(1).split("&").reduce(function(r, p) {
					r[decodeURIComponent(p.split("=")[0])] = decodeURIComponent(p.split("=")[1]); return r;
				}, {});
				new app.App("body", {
					id: "es",
					base_uri: args["base_uri"] || base_uri,
					auth_user : args["auth_user"] || "",
					auth_password : args["auth_password"],
					dashboard: args["dashboard"]
				});
			};
		</script>
		<link rel="icon" href="base/favicon.png" type="image/png">
	</head>
</html>
