<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%
	String domain = DingConfig.newInstance().getDingDomain();
	String url = (String)request.getAttribute("message_url");
	if(url.indexOf("http")!=url.lastIndexOf("http")){
		url = url.replaceFirst(domain, "");
	}
	if(url.indexOf("https//")>-1){
		url = url.replaceFirst("https//", "https://");
	}
	if(url.indexOf("http//")>-1){
		url = url.replaceFirst("http//", "http://");
	}
	url = StringUtil.setQueryParameter(url, "mdingclose", "1");
	request.setAttribute("murl", url);
%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
<title><bean:message bundle="third-ding" key="third.ding.ding.loading" /></title>
</head>
<body>
<script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
<script type="text/javascript">
$(function(){
	 //平台、设备和操作系统
    var system = {
        win: false,
        mac: false,
        xll: false
    };
    //检测平台
    var p = navigator.platform;
    system.win = p.indexOf("Win") == 0;
    system.mac = p.indexOf("Mac") == 0;
    system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
    //跳转语句
    if (system.win || system.mac || system.xll) {//pc端 
    	window.location.href = "${pc_message_url}";
    }else{
    	window.location.href = "${murl}";
    }
    
});

</script>

<div id="_load"> 
	<c:out value="跳转中..."></c:out><br>
</div>
</body>

</html>

