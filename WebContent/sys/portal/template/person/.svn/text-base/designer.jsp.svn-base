<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ page import="com.landray.kmss.sys.ui.util.SysUiConfigUtil"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<script type="text/javascript">
		seajs.use(['theme!list', 'theme!form', 'theme!portal']);
	</script>
	<title>
		<portal:title/>
	</title> 
</head>
<body class="portal_body">
	<div style="margin: 0px auto;width: 980px;display: table;">
		<div class="design_heder">
			<portal:header/>
		</div>
		
		<div>
			<!--个人信息区 -->
			<%@ include file="/sys/person/portal/persontitle.jsp" %>
		</div>
		<div style="width:980px; min-width:980px; margin:10px auto 10px auto;">
			<!--内容区 -->
			<template:block name="content"></template:block>
		</div>
		
		<!-- 页脚 -->
		<div style="margin: 0px auto;height: 50px;border: 1px #e5e5e5 solid;background: #c8c8c8;text-align: center;">
		 	<portal:footer/>
		</div>
	</div> 
</body>
</html>
