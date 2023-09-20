<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.search.forms.SysSearchMainForm"%>
<%@ page import="com.landray.kmss.sys.search.web.SearchConditionEntry" %>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<html:form action="/sys/search/sys_search_main/sysSearchMain.do" onsubmit="return validateSysSearchMainForm(this);">
<div id="optBarDiv">
	<input type=button value="<bean:message bundle="sys-search" key="search.btn.rtnToTmp"/>"
		onclick="submitForm('rtnEditTemplate');">
	<input type=button value="<bean:message bundle="sys-search" key="search.btn.nextToMainEdit"/>"
		onclick="submitForm('editMain');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message  bundle="sys-search" key="table.sysSearchMain"/><bean:message key="button.edit"/></p>

<center>
<html:hidden property="fdId"/>
<html:hidden property="fdModelName"/>
<html:hidden property="fdTemplateModelName"/>
<html:hidden property="fdKey"/>
<html:hidden property="fdCondition" />
<html:hidden property="fdDisplay" />
<html:hidden property="fdOrderBy" />
<html:hidden property="fdTemplateId"/>
<html:hidden property="fdTemplateName"/>
<html:hidden property="fdParemNames"/>
<html:hidden property="authSearchReaderNames"/>
<html:hidden property="fdCategoryId"/>
<html:hidden property="fdCategoryName"/>
<table class="tb_normal" width="750px">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-search" key="sysSearchMain.fdName"/>
		</td>
		<td>
			<xform:text property="fdName" showStatus="readOnly" style="width:80%"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-search" key="sysSearchMain.fdOrder"/>
		</td>
		<td>
			<xform:text property="fdOrder" showStatus="readOnly" style="width:80%"></xform:text>
		</td>
	</tr>
	<c:if test="${not empty sysSearchMainForm.fdTemplateId}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-search" key="sysSearchMain.fdTemplateId"/>
		</td>
		<td>
			<c:out value="${sysSearchMainForm.fdTemplateName}"/>
		</td>
	</tr>
	</c:if>
	<tr class="tr_normal_title">
		<td colspan="2">
			<bean:message bundle="sys-search" key="sysSearchMain.fdParemNames"/>
			<html:hidden property="fdParemNames"/>
		</td>
	</tr>
	<c:if test="${'com.landray.kmss.km.agreement.model.KmAgreementApply'==param.fdModelName}">
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-search" key="py.shuoming"/>
		</td>
		<td>
			<bean:message bundle="sys-search" key="py.infoshuoming"/>
		</td>
	</c:if>
	<%-- 条件选择 --%>
	<tr>
	<td colspan="2">
		<table width=100% class="tb_normal">
			<%@ include file="/sys/search/search_condition_entry.jsp"  %>
		</table>
	</td>
	</tr>
	
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<script>
Com_IncludeFile("dialog.js|data.js|calendar.js|dialog.js");
function submitForm(method){
	Com_Submit(document.sysSearchMainForm, method);
}
</script>
<html:javascript formName="sysSearchMainForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>