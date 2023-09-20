<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil,java.lang.String" %>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
<%
	pageContext.setAttribute("enableAttachmentSignature", SysAttConfigUtil.isEnableAttachmentSignature());
%>
<div style="margin-top:25px">
<p class="configtitle"><bean:message key="attachment.signature.config" bundle="sys-attachment" /></p>
	<center>
<table class="tb_normal" width=90%>
	<tr>
		<td class="td_normal_title" width=30%>
			<bean:message key="attachment.signature.config" bundle="sys-attachment" />
		</td>
		<td>
			<ui:switch property="value(enableAttachmentSignature)" checkVal="1" unCheckVal="0"
					   enabledText="${lfn:message('sys-ui:attachment.signature.open')}" disabledText="${lfn:message('sys-ui:attachment.signature.forbidden')}" onValueChange="operateEnableAttSignature(this.checked)"></ui:switch>
            <br>
			<div id="div_signature">
				<xform:radio property="value(attachementSignatureType)" showStatus="edit">
					<xform:simpleDataSource value="1"><bean:message key="attachment.signature.jg" bundle="sys-attachment" />&nbsp;&nbsp;&nbsp;&nbsp;</xform:simpleDataSource>
					<kmss:ifModuleExist path="/third/dianju/">
						<xform:simpleDataSource value="2"><bean:message key="attachment.signature.dianju" bundle="sys-attachment" /></xform:simpleDataSource>
					</kmss:ifModuleExist>

				</xform:radio>
				<br>
			</div>
	</tr>


</table>

<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="commitMethod();" order="1" ></ui:button>
</div>
</center>
</div>
</html:form>
<script>
	Com_IncludeFile("jquery.js");
</script>
<script>
$(document).ready(function (){
	var isEnableSignature = "${enableAttachmentSignature}";
	if (isEnableSignature == 'true') {
		operateEnableAttSignature(true);
	} else {
		operateEnableAttSignature(false);
	}


});
function commitMethod(value){
	Com_Submit(document.sysAppConfigForm, 'update');
}

function operateEnableAttSignature(value) {
	if(value == true) {
		$("#div_signature").show();
	} else {
		$("#div_signature").hide();
	}
}
</script>


</template:replace>
</template:include>