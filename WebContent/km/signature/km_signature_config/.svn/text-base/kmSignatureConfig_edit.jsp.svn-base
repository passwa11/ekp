<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<html:form action="/km/signature/km_signature_config/kmSignatureConfig.do" >
<div style="margin-top:25px">
<p class="configtitle"><bean:message bundle="km-signature" key="table.kmSignatureConfig"/></p>
<center>
<table class="tb_normal" width=95%>
    <tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-signature" key="table.kmSignatureConfig.fdIsAutoSign"/>
		</td>
		<td colspan="3">
			<ui:switch property="fdIsAutoSign" enabledText="${lfn:message('km-signature:table.kmSignatureConfig.fdIsAutoSign.open')}" disabledText="${lfn:message('km-signature:table.kmSignatureConfig.fdIsAutoSign.close')}"
			 checked="${kmSignatureConfigForm.fdIsAutoSign}" ></ui:switch>
			<br>
			<span><bean:message bundle="km-signature" key="table.kmSignatureConfig.fdIsAutoSign.tips" /></span>
		</td>		
	</tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.kmSignatureConfigForm, 'update');" order="1" ></ui:button>
</div>
</center>
</div>
<html:hidden property="method_GET"/>
</html:form>
<script>
var kmSignatureConfigFormValidator = new $KMSSValidation(document.forms['kmSignatureConfigForm']);
Com_Parameter.event["submit"].push(function() {
	return kmSignatureConfigFormValidator.validate();
});
</script>
</template:replace>
</template:include>