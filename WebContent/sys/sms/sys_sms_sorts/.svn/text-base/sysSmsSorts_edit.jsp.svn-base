<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/sys/sms/sys_sms_sorts/sysSmsSorts.do" onsubmit="return validateSysSmsSortsForm(this);">
<div id="optBarDiv">
	<c:if test="${sysSmsSortsForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysSmsSortsForm, 'update');">
	</c:if>
	<c:if test="${sysSmsSortsForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysSmsSortsForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysSmsSortsForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message  bundle="sys-sms" key="table.sysSmsSorts"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-sms" key="sysSmsSorts.fdName"/>
		</td><td colspan=3>
			<html:text property="fdName" style="width=100%"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-sms" key="sysSmsSorts.docContent"/>
		</td><td colspan=3>
			<html:textarea property="docContent" style="width=100%"></html:textarea>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<html:javascript formName="sysSmsSortsForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>
