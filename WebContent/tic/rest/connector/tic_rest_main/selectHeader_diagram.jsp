<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="head">
</template:replace>
<template:replace name="content">
<table class="tb_normal" width=98%>

	<tr>
	<td colspan="2" class="td_normal_title">${ lfn:message('tic-rest-connector:ticRestMain.fdReqHeader.add.reqAuth.info') }</td>
	</tr>
	<tr>
	<td class="td_normal_title" width="25%">${ lfn:message('tic-rest-connector:ticRestMain.fdReqHeader.add.reqAuth.username') }</td>
	<td><input name="username" class="inputsgl" value="" type="text" style="width:85%" /></td>
	</tr>
	<tr>
	<td class="td_normal_title" width="25%">${ lfn:message('tic-rest-connector:ticRestMain.fdReqHeader.add.reqAuth.pwd') }</td>
	<td><input name="pwd" class="inputsgl" value="" type="text" style="width:85%" /></td>
	</tr>

<center>
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;padding-bottom:5px;">
			<ui:button style="width:80px" onclick="doOK();" text="${lfn:message('button.ok') }" />
			<ui:button style="width:80px" onclick="Com_CloseWindow();" text="${lfn:message('button.close') }" />
		</div>
</center>
</table>
<script>
var ret={};
function doOK(){
	ret={"username":document.getElementsByName("username")[0].value,
	"pwd":document.getElementsByName("pwd")[0].value};
	window.$dialog.hide(ret);
}
</script>
</template:replace>
</template:include>