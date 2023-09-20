<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js", null, "js");
</script>
<html:form action="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do">
<div id="optBarDiv">
	<c:if test="${sysMportalModuleCateForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysMportalModuleCateForm, 'update');">
	</c:if>
	<c:if test="${sysMportalModuleCateForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysMportalModuleCateForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysMportalModuleCateForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-mportal" key="sysMportalModuleCate.title"/></p>

<center>
<table class="tb_normal" width=95%>
		<html:hidden property="fdId"/>
	<tr>
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalModuleCate.fdName"/>
		</td><td width=35%>
			<xform:text property="fdName" style="width:85%" required="true"  />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalModuleCate.fdOrder"/>
		</td><td width=35% colspan="3">
			<html:text property="fdOrder"/>
		</td>	
	</tr>
	<c:if test = "${sysMportalModuleCateForm.method_GET == 'edit'}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalModuleCate.docCreateTime"/>
		</td><td width=35%>
			<html:hidden property="docCreateTime"/>
			<c:out value="${sysMportalModuleCateForm.docCreateTime}"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalModuleCate.docCreator"/>
		</td><td width=35%>
			<html:hidden property="docCreatorId"/>
			<c:out value="${sysMportalModuleCateForm.docCreatorName}"/>
		</td>
	</tr>
	</c:if>
</table>
</center>
<html:hidden property="method_GET"/>
<script>
    $KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>