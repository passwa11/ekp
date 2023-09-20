<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<style>
.expand{
	display: none;
}
</style>
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do" onsubmit="return validateAppConfigForm(this);">
<div style="margin-top:25px">
<p class="configtitle"><bean:message key="sysFilestore.tree.convertClear.displayConfig" bundle="sys-filestore" /></p>
<center>
<table class="tb_normal" width=90%>
	<tr>
		<td class="td_normal_title" colspan="3"><ui:switch id="attconvert.enabled"
				checked="${attConvertIsEnabled}" property="value(kmssConvertFileClearEnabled)"
				onValueChange="config_clear();"
				enabledText="${ lfn:message('sys-filestore:sysFilestore.convertClear.enable') }"
				disabledText="${ lfn:message('sys-filestore:sysFilestore.convertClear.enable') }"></ui:switch>
		<span class="message">&nbsp;${lfn:message('sys-filestore:sysFilestore.convertClear.displayConfig.tip')}</span>		
		</td>
	</tr>
	<tr name="clear_tr" style="display: none;">
	  <td class="td_normal_title" width=20%>
		<bean:message key="sysFilestore.convertClear.kmssConvertFileClearMonth" bundle="sys-filestore" />
	  </td>
	  <td colspan="3">
			<bean:message key="sysFilestore.convertClear.kmssConvertFileClearMonth.tip1" bundle="sys-filestore" /><xform:text property="value(kmssConvertFileClearMonth)" subject="${lfn:message('sys-filestore:sysFilestore.convertClear.kmssConvertFileClearMonth')}"  style="width:50px;" showStatus="edit" validators="digits min(0)"/><bean:message key="sysFilestore.convertClear.kmssConvertFileClearMonth.tip2" bundle="sys-filestore" /><br>
	  </td>
	</tr>
	<tr name="clear_tr" style="display: none;">
	  <td class="td_normal_title" width=20%>
		<bean:message key="sysFilestore.convertClear.kmssConvertFileClearBatchNum" bundle="sys-filestore" />
	  </td>
	  <td colspan="3">
			<xform:text property="value(kmssConvertFileClearBatchNum)" subject="${lfn:message('sys-filestore:sysFilestore.convertClear.kmssConvertFileClearBatchNum')}" style="width:50px;" showStatus="edit" validators="digits min(1) max(10000)"/><br>
			<span class="message">&nbsp;<bean:message key="sysFilestore.convertClear.kmssConvertFileClearBatchNum.tip" bundle="sys-filestore" /></span>
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
$KMSSValidation();

function validateAppConfigForm(thisObj){
	return true;
}
	
function commitMethod(value){
	 Com_Submit(document.sysAppConfigForm, 'update');
}

function config_clear() {
	var clear = $("tr[name=clear_tr]");
	var isChecked = "true" == $("input[name='value\\\(kmssConvertFileClearEnabled\\\)']").val();
	if (isChecked) {
		clear.show();
	} else {
		clear.hide();
	}

	clear.find("input").each(function() {
		$(this).attr("disabled", !isChecked);
	});
}

LUI.ready(function() {	
	config_clear();
});
</script>
</template:replace>
</template:include>