<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("docutil.js|calendar.js|dialog.js|doclist.js|optbar.js");
</script>
<html:form action="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do"
	onsubmit="return validateSysSmsUpperlimitForm(this);">
	<div id="optBarDiv"><c:if
		test="${sysSmsUpperlimitForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysSmsUpperlimitForm, 'update');">
	</c:if> <c:if test="${sysSmsUpperlimitForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysSmsUpperlimitForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysSmsUpperlimitForm, 'saveadd');">
	</c:if> <input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();"></div>

	<p class="txttitle"><bean:message bundle="sys-sms"
		key="table.sysSmsUpperlimit" /></p>

	<center>
	<table class="tb_normal" width=95%>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-sms" key="sysSmsLimitperson.fdPersonsId" /></td>
			<td colspan="3"><html:hidden property="theSmsPersonIds" /><html:textarea
				property="theSmsPersonNames" style="width=95%" styleClass="inputsgl"
				readonly="true">
			</html:textarea><a href="#"
				onclick="Dialog_Address(true,'theSmsPersonIds','theSmsPersonNames',';',ORG_TYPE_PERSON);"><bean:message
				key="dialog.selectOther" /></a></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-sms" key="sysSmsUpperlimit.fdByperiod" /></td>
			<td width=35%><sunbor:enums
				enumsType="sysSmsUpperlimitByperiods" property="fdByperiod"
				elementType="select" bundle="sys-sms" /></td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-sms" key="sysSmsUpperlimit.fdUpperlimit" /></td>
			<td width=35%><html:text property="fdUpperlimit" /></td>

		</tr>
	</table>
	</center>
	<html:hidden property="method_GET" />
</html:form>
<html:javascript formName="sysSmsUpperlimitForm" cdata="false"
	dynamicJavascript="true" staticJavascript="false" />
<%@ include file="/resource/jsp/edit_down.jsp"%>
