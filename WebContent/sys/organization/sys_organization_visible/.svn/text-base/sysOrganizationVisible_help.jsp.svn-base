<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/watermarkPcCommon.jsp" %>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil,java.lang.String" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><bean:message bundle="sys-organization" key="sysOrganizationVisible.config.help"/></title>
	<style type="text/css">
		body{
			padding-left:50px;
			font-size: 14px;
		}
		p{
			margin-top:0px;
			line-height: 32px;
		}
		.number{
			margin:0 5px;
			color: red;	
		}
		.text{
			color: #4372C7;
		}
		.secondLine{
			margin-left: 42px;
		}
		.img{
			display:block;
			margin: 5px;
			width: 432px;
    		height: 417px;
		}
		.warning{
			color: red;
		}
	</style>
</head>
<body>
	<h2><bean:message bundle="sys-organization" key="sysOrganizationVisible.config"/></h2>
	<p>
		<span class='number'>0</span><bean:message bundle="sys-organization" key="sysOrganizationVisible.config.value0"/>
		<span class='number'>1</span><bean:message bundle="sys-organization" key="sysOrganizationVisible.config.value1"/>
		<span class='number'>2</span><bean:message bundle="sys-organization" key="sysOrganizationVisible.config.value2"/>
		<br/>
		<bean:message bundle="sys-organization" key="sysOrganizationVisible.config.demo"/>：<span class='firstLine'>1、<bean:message bundle="sys-organization" key="sysOrganizationVisible.config.demo1"/> </span>
		<br/>
		<span class='secondLine'>2、<bean:message bundle="sys-organization" key="sysOrganizationVisible.config.demo2"/></span>
		<br/>
		<bean:message bundle="sys-organization" key="sysOrganizationVisible.config.example"/>：
		<%
		String currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
		if(currentLocaleCountry != null && currentLocaleCountry.equals("US")){
			%>
			<img class='img' src="sysOrganizationVisible_help_English.png" />
			<% 
		}
		if(currentLocaleCountry != null && currentLocaleCountry.equals("CN")){
			%>
			<img class='img' src="sysOrganizationVisible_help.png" />
			<% 
		}
		%>
		<bean:message bundle="sys-organization" key="sysOrganizationVisible.config.explain1"/>
		<br/>
		<bean:message bundle="sys-organization" key="sysOrganizationVisible.config.explain2"/>
		<br/>
		<span class='warning'><bean:message bundle="sys-organization" key="sysOrganizationVisible.config.attention"/></span>
	</p>
	<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>