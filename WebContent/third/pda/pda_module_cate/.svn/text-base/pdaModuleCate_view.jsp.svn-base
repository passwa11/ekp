<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
		<kmss:auth requestURL="/third/pda/pda_module_cate/pdaModuleCate.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('pdaModuleCate.do?method=edit&fdId=${param.fdId}','_self');">
		</kmss:auth>
		<kmss:auth requestURL="/third/pda/pda_module_cate/pdaModuleCate.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.delete"/>"
				onclick="if(!confirmDelete())return;Com_OpenWindow('pdaModuleCate.do?method=delete&fdId=${param.fdId}','_self');">
		</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="third-pda" key="pdaMoudleCategoryList.title"/></p>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="pdaModuleCateForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-pda" key="pdaMoudleCategoryList.fdName"/>
		</td><td width=35%>
			<c:out value="${pdaModuleCateForm.fdName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-pda" key="pdaMoudleCategoryList.serial"/>
		</td><td width=35%>
			<c:out value="${pdaModuleCateForm.fdOrder}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-pda" key="pdaMoudleCategoryList.fdCreateTime"/>
		</td><td width=35%>
			<c:out value="${pdaModuleCateForm.docCreateTime}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-pda" key="pdaMoudleCategoryList.docCreator"/>
		</td><td width=35%>
			<c:out value="${pdaModuleCateForm.docCreatorName}" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>