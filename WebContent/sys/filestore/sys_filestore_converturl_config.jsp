<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<style>
.expand{
	display: none;
}
</style>
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
<div style="margin-top:25px">
<p class="configtitle"><bean:message key="sysFilestore.tree.convertUrl.displayConfig" bundle="sys-filestore" /></p>
<center>
<table class="tb_normal" width=90%>
    <tr>
		<td class="td_normal_title" width=15%><bean:message key="sysFilestore.config.suwell.enable" bundle="sys-filestore" /></td>
		<td>
			<ui:switch property="value(suwellConvretEnabled)"
				onValueChange="suwelluChangeEvent();"
				enabledText=""
				disabledText="" 
				>
			</ui:switch>
		</td>
	</tr>
	<tr class="thirdSuwell">
	  <td class="td_normal_title" width=20%>
		<bean:message key="sysFilestore.config.suwellConvertUrl" bundle="sys-filestore" />
	  </td>
	  <td colspan="3">
			<xform:text property="value(suwellConvertUrl)" subject="${lfn:message('sys-filestore:sysFilestore.config.suwellConvertUrl')}"  style="width:85%" showStatus="edit"/><br>
			<span class="message"><bean:message key="sysFilestore.config.suwellConvertUrl.tip" bundle="sys-filestore" /></span>
	  </td>
	</tr>
	<tr class="thirdSuwell">
	  <td class="td_normal_title" width=20%>
		<bean:message key="sysFilestore.config.suwellResultPath" bundle="sys-filestore" />
	  </td>
	  <td colspan="3">
			<xform:text property="value(suwellResultPath)" subject="${lfn:message('sys-filestore:sysFilestore.config.suwellResultPath')}"  style="width:85%" showStatus="edit"/><br>
			<span class="message"><bean:message key="sysFilestore.config.suwellResultPath.tip" bundle="sys-filestore" /></span>
	  </td>
	</tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="commitMethod();" order="1" ></ui:button>
</div>
</center>
</div>
</html:form> 
<script>
Com_IncludeFile("jquery.js");
</script>
<script>
function commitMethod(value){
	 Com_Submit(document.sysAppConfigForm, 'update');
}

function suwelluChangeEvent() {
	var autoCategoryTag = "true" == $("input[name='value\\\(suwellConvretEnabled\\\)']").val();

	if (autoCategoryTag) {
		$('.thirdSuwell').css('display', 'table-row');
	}else{
		$('.thirdSuwell').hide();
	}
}

LUI.ready(function() {
	suwelluChangeEvent();

});
</script>
</template:replace>
</template:include>