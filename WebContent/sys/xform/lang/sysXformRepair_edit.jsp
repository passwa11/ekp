<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="提交" onclick="document.forms[0].submit()"></ui:button>
		</ui:toolbar>
	</template:replace>
<template:replace name="content">	
<html:form action="/km/review/km_review_main/kmReviewMain.do?method=repairColon">
<p class="txttitle">修复流程管理模块表单控件冒号数据</p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			修复的控件ID
		</td><td width="35%">
			<input type="text" name="fdControlId" class="inputsgl" style="width:85%">
		</td>
		<td class="td_normal_title" width=15%>
			模板ID
		</td><td width="35%">
			<input type="text" name="fdTemplateId" class="inputsgl" style="width:85%">
		</td>
	</tr>
</table> 
</center>
<script>
</script>
</html:form>
</template:replace>
</template:include>