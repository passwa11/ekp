<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="content">	
<br><br>
<center>
<script  type="text/javascript">
		seajs.use([ 'lui/jquery','lui/parser','lui/dialog'],function($,parser,dialog) {
			//确认
			window.clickOK=function(){
				var field = document.getElementsByName("fdName")[0];
				if(field.value==""){
					dialog.alert("<kmss:message key="errors.required" bundle="km-imeeting" />");
					return;
				}
				var rtn = field.value;
				$dialog.hide(rtn);
			};
		});
</script>
<bean:message bundle="km-imeeting" key="kmImeetingSeatTemplate.fdName" />
<xform:text property="fdName" required="true" showStatus="edit"></xform:text>
<br><br>
<ui:button text="${lfn:message('button.ok') }" onclick="clickOK();"></ui:button>
<ui:button style="padding-left:10px"  text="${lfn:message('button.cancel') }" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();"></ui:button>
</center>
</template:replace>
</template:include>