<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<!doctype html>
<html>
<%
    String locale = UserUtil.getKMSSUser(request).getLocale().toString();
 %>
    <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title>elasticsearch-head</title>
        <link rel="stylesheet" href="dist/base/reset.css">
        <link rel="stylesheet" href="dist/vendor.css">
        <link rel="stylesheet" href="dist/app.css">
        <script src="dist/i18n.js" data-baseDir="dist/lang" data-langs="en,fr,pt,zh,<%=locale%>"></script>
        <script src="dist/vendor.js"></script>
        <script src="dist/app.js"></script>
        <script>
            window.onload = function() {
                if(location.href.contains("/elasticsearch-head-master/")) {
                    var base_uri = location.href.replace(/elasticsearch-head-master\/.*/, 'head');
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
        <link rel="icon" href="dist/base/favicon.png" type="image/png">
    </head>
    <body>
        <input type="hidden" name="locale" id="localeInput" value="<%=locale%>"/>
    </body>
</html>


