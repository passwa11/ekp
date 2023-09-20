<%@page import="com.landray.kmss.third.ecalendar.EcalendarConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
String addDomain = new EcalendarConfig().getValue("calendar.integrate.sso.addDomain");
String user = (String)request.getAttribute("f_user");
if(user!=null && "false".equals(addDomain) && user.contains("@")){
	user = user.substring(0,user.indexOf("@"));
}
request.setAttribute("f_user", user);
%>

<script type="text/javascript">


window.onload = function(){
	
	document.logonForm.submit();
}

function login() {
	document.logonForm.submit();
	/*
	var oD = new Date();
    oD.setTime(oD.getTime() + 2 * 7 * 24 * 60 * 60 * 1000);
    var sA = "acc=" + (gbid("chkBsc") && gbid("chkBsc").checked ? 1 : 0);
    var sL = "lgn=" + gbid("username").value;
    //document.cookie = "logondata=" + sA + "&" + sL + "; expires=" + oD.toUTCString();
    document.cookie = "PrivateComputer=true; path=/; Domain=test.com; expires=" + oD.toUTCString();

document.cookie = "PBack=0; path=/;Domain=test.com";
*/
}
</script>
<%-- 
<iframe src="https://ad2.test.com/owa/?isEkp=true"></iframe>
--%>
<form id="login_form" name="logonForm" action="${f_page}" method="post" target="_self" autocomplete="off">
	<input type="hidden" name="destination" id="destination" value="${f_owa }"/>
	<input type="hidden" name="flags" value="${f_style}"/>
	<input type="hidden" name="forcedownlevel" value="0"/> 
	<input type="hidden" id="rdoPblc" name="trusted" value="0"/>
	<input type="hidden" id="username" name="username" value="${f_user}" />
	<input type="hidden" id="password" name="password" value="${f_pass}" />
</form>
