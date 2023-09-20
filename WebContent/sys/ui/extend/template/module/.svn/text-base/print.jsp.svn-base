<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
seajs.use(['theme!form']);
window.isPrintModel = true;
</script>
<title>
	<template:block name="title" />
</title>
<template:block name="head" />
</head>
<body class="lui_print_body">
<div id="optBtnBarDiv" class="btnprint">
<template:block name="toolbar"/>
</div>
<div class="lui_print_main_content">
<table class="tempTB" style="margin: 0px auto;width:100%;">
	<tr>
		<td valign="top" class="lui_form_content_td">
			<div class="lui_form_content">
				<template:block name="content" />
			</div>
		</td>
	</tr>
</table>
</div>
<div style="height:20px;"></div>
<c:import url="/sys/profile/i18n/quicklyMultiLangEdit.jsp"></c:import>
</body>
</html>

