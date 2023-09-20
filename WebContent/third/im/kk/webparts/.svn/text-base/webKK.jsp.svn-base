<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<%@ page import="com.landray.kmss.third.im.kk.*"%>

<%
	String webKKUrl = new KkConfig().getValue("third.im.kk.webkk.urlPrefix");
	if (StringUtil.isNull(webKKUrl)) {
		webKKUrl = "noWebKKUrl";
	}
	request.setAttribute("WebKKUrl", webKKUrl);
%>
<script>
	Com_IncludeFile("document.js", "style/" + Com_Parameter.Style + "/doc/");
	var webKKUrl = "${WebKKUrl}";

	function forwardToSnsWebKK() {
		var originalLocation = new String(window.location);
		var param = originalLocation.substring(originalLocation.indexOf("?"));
		if (webKKUrl == "noWebKKUrl") {
			document.getElementById("hintNoWebKKUrl").innerHTML = "没有配置webKK服务器地址或者配置了但没重启应用系统";
		} else {
			window.location = webKKUrl + "/webkk" + param;
		}
	}
</script>
</head>
<body onload="forwardToSnsWebKK();">
<center>
<p id="hintNoWebKKUrl"></p>
</center>
</body>
</html>