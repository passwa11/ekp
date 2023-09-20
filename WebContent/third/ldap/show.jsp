<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<%@ include file="/resource/jsp/watermarkPcCommon.jsp" %>
<%@ page import="com.landray.kmss.third.ldap.SampleDataShow" %>

<html>
<head>
<title>LDAP样本采集</title>
<style>
body,table,td{
	font-size:12px;
	background: #E6EAE9; 
}

table{
	border-collapse:collapse; 
	width: 90%; 
	padding: 0; 
	margin: 0; 
}
td {
	border-style:solid;
	border-color:#85CFFE;
	border-width: 1px 1px 1px 1px;
	padding:5px 1px 6px 1px;
}
.tdb{
	background: #CAE8EA  no-repeat; 
	width:22%
}

</style>
<script>
function retSetting(){
	window.location.href="<c:url value="/third/ldap/setting.do"/>?method=edit";
}
</script>
</head>
<body>
<div align="right">
<input type="button" name="r" value="${lfn:message('third-ldap:kmss.ldap.sample.returnSetting')}" onclick="retSetting();">
<input type="button" name="c" value="${lfn:message('third-ldap:kmss.ldap.sample.closeWindows')}" onclick="window.close();">
</div>
<%
java.util.Map<String,String> dataMap = (java.util.Map<String,String>)request.getAttribute("dataMap");
SampleDataShow sd = new SampleDataShow();
try{
	if (dataMap != null && dataMap.containsKey("kmss.ldap.config.password")) {
		String value = dataMap.get("kmss.ldap.config.password");
		value = com.landray.kmss.third.ldap.LdapUtil.desDecrypt(value);
		dataMap.put("kmss.ldap.config.password", value);
	}
	sd.handle(dataMap);
	out.println(sd.getOtherInfo());
	out.println(sd.getDeptInfo());
	out.println(sd.getPersonInfo());
	out.println(sd.getGroupInfo());
	out.println(sd.getPostInfo());
}catch(javax.naming.CommunicationException cex){
%>
	<font color='red'>${lfn:message('third-ldap:kmss.ldap.sample.message.ConnFail')}<%=cex.getMessage() %></font>
<%	
}catch(javax.naming.AuthenticationException aex){
%>	
   <font color='red'>${lfn:message('third-ldap:kmss.ldap.sample.message.loginFail')}<%=aex.getMessage() %></font>
<%
}catch(javax.naming.NameNotFoundException nex){
%>	
   <font color='red'>${lfn:message('third-ldap:kmss.ldap.sample.message.NameNotFound')}<%=nex.toString() %></font>
<%
} catch (Exception ex) {
	ex.printStackTrace();
}
%>
<body>
</html>
