<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	request.setAttribute("sys.ui.theme", "sky_blue");
%>
<template:include ref="default.simple">
	<template:replace name="title">配置容器</template:replace>
	<template:replace name="body">
	<ui:toolbar layout="sys.ui.toolbar.float" count="10" var-navwidth="100%">
		<ui:button onclick="onEnter()" text="${ lfn:message('sys-portal:sysPortalPage.msg.enter') }"></ui:button>
	</ui:toolbar>
	<script>
		seajs.use(['theme!form']);
		</script>
<script>
	function onReady(){
		if(window.$dialog == null){
			window.setTimeout(onReady, 100);
			return
		}
		window.$ = LUI.$;
		var dp = window.$dialog.dialogParameter;
		$("#vSpacing").val($.trim(dp.vSpacing));
	}

	LUI.ready(onReady);
	function onEnter(){
		var data = {};
		data.vSpacing = $("#vSpacing").val();
		window.$dialog.hide(data);
	}

	function DigitInput(el,e) {
		el.value=el.value.replace(/\D/gi,"");
	}
</script>
<br>
<br>
<table class="tb_normal" style="width: 300px;">
	<tbody>
		<tr>
			<td>${ lfn:message('sys-portal:sysPortalPage.desgin.c.tds') }:</td>
			<td><input type="text" id="vSpacing" name="vSpacing" value="10" style="width:50px;" onkeyup="DigitInput(this,event);" onblur="DigitInput(this,event)"/>px</td>
		</tr>
	</tbody>
</table> 

	</template:replace>
</template:include>