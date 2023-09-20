<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("docutil.js|calendar.js|dialog.js|doclist.js|optbar.js");
</script>
<html:form action="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do"
	onsubmit="return validateSysSmsSortofpeopleForm(this);">
	<div id="optBarDiv"><c:if
		test="${sysSmsSortofpeopleForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysSmsSortofpeopleForm, 'update');">
	</c:if> <c:if test="${sysSmsSortofpeopleForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysSmsSortofpeopleForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysSmsSortofpeopleForm, 'saveadd');">
	</c:if> <input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();"></div>

	<p class="txttitle"><bean:message bundle="sys-sms"
		key="table.sysSmsSortofpeople" /></p>

	<center>
	<table class="tb_normal" width=95%>
		<tr>

			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-sms" key="sysSmsSortofpeople.fdName" /></td>
			<td width=35%><html:text property="fdName" /><span class="txtstrong">*</span></td>

			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-sms" key="sysSmsSortofpeople.fdOrder" /></td>
			<td width=35%><html:text property="fdOrder" /></td>

		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-sms" key="sysSmsSortedperson.fdSortedpersonsId" /></td>
			<td colspan="3"><html:hidden property="sortedPersonIds" /> <html:textarea
				property="sortedPersonNames" styleClass="inputsgl" style="width=95%"
				readonly="true"></html:textarea><a href="#"
				onclick="Dialog_Address(true,'sortedPersonIds','sortedPersonNames',';',ORG_TYPE_PERSON);"><bean:message
				key="dialog.selectOther" /></a></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-sms" key="sysSmsSortofpeople.docContent" /></td>
			<td colspan=3><html:textarea property="docContent"
				style="width=100%"></html:textarea></td>
		</tr>
	</table>
	</center>
	<html:hidden property="method_GET" />
</html:form>
<html:javascript formName="sysSmsSortofpeopleForm" cdata="false"
	dynamicJavascript="true" staticJavascript="false" />
<%@ include file="/resource/jsp/edit_down.jsp"%>
