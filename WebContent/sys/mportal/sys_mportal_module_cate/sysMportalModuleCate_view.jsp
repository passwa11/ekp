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
		<kmss:auth requestURL="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('sysMportalModuleCate.do?method=edit&fdId=${param.fdId}','_self');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/mportal/sys_mportal_module_cate/sysMportalModuleCate.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.delete"/>"
				onclick="if(!confirmDelete())return;Com_OpenWindow('sysMportalModuleCate.do?method=delete&fdId=${param.fdId}','_self');">
		</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-mportal" key="sysMportalModuleCate.title"/></p>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="sysMportalModuleCateForm" property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalModuleCate.fdName"/>
		</td><td width=35%>
			<c:out value="${sysMportalModuleCateForm.fdName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalModuleCate.fdOrder"/>
		</td><td width=35%>
			<c:out value="${sysMportalModuleCateForm.fdOrder}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalModuleCate.docCreateTime"/>
		</td><td width=35%>
			<c:out value="${sysMportalModuleCateForm.docCreateTime}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-mportal" key="sysMportalModuleCate.docCreator"/>
		</td><td width=35%>
			<c:out value="${sysMportalModuleCateForm.docCreatorName}" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>