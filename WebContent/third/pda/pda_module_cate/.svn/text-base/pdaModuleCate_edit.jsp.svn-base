<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js", null, "js");
</script>
<html:form action="/third/pda/pda_module_cate/pdaModuleCate.do">
<div id="optBarDiv">
	<c:if test="${pdaModuleCateForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.pdaModuleCateForm, 'update');">
	</c:if>
	<c:if test="${pdaModuleCateForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.pdaModuleCateForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.pdaModuleCateForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="third-pda" key="pdaMoudleCategoryList.title"/></p>

<center>
<table class="tb_normal" width=95%>
		<html:hidden property="fdId"/>
	<tr>
		
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-pda" key="pdaMoudleCategoryList.fdName"/>
		</td><td width=35%>
			<xform:text property="fdName" style="width:85%" required="true"  />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-pda" key="pdaMoudleCategoryList.serial"/>
		</td><td width=35% colspan="3">
			<html:text property="fdOrder"/>
		</td>	
	</tr>
	<c:if test = "${pdaModuleCateForm.method_GET == 'edit'}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-pda" key="pdaMoudleCategoryList.fdCreateTime"/>
		</td><td width=35%>
			<html:hidden property="docCreateTime"/>
			<c:out value="${pdaModuleCateForm.docCreateTime}"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-pda" key="pdaMoudleCategoryList.docCreator"/>
		</td><td width=35%>
			<html:hidden property="docCreatorId"/>
			<c:out value="${pdaModuleCateForm.docCreatorName}"/>
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