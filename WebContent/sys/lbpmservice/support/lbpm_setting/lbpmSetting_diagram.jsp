<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="head">
</template:replace>
<template:replace name="content">
<table>
<tr>
<td>
<img id="detail" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_setting/images/${HtmlParam.key}.png" width="100%">
<center>
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;padding-bottom:5px;">
			<ui:button style="width:80px" onclick="Com_CloseWindow();" text="${lfn:message('button.close') }" />
		</div>
</center>
</td>
</tr>
</table>
</template:replace>
</template:include>