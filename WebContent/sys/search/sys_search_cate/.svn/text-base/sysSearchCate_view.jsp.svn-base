<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirm_delete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
<kmss:auth requestURL="/sys/search/sys_search_cate/sysSearchCate.do?method=edit&fdId=${sysSearchCateForm.fdId}&fdModelName=${JsParam.fdModelName}" requestMethod="GET">
	<input type="button" value="<bean:message key="button.edit"/>"
		onClick="Com_OpenWindow('sysSearchCate.do?method=edit&fdId=<bean:write name="sysSearchCateForm" property="fdId" />','_self');">
</kmss:auth>
<kmss:auth requestURL="/sys/search/sys_search_cate/sysSearchCate.do?method=delete&fdId=${sysSearchCateForm.fdId}&fdModelName=${JsParam.fdModelName}" requestMethod="GET">
	<input type="button" value="<bean:message key="button.delete"/>"
		onClick="if(!confirm_delete())return;Com_OpenWindow('sysSearchCate.do?method=delete&fdId=<bean:write name="sysSearchCateForm" property="fdId" />','_self');">
</kmss:auth>
<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-search" key="table.sysSearchCate"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-search" key="sysSearchCate.fdParent"/>
		</td><td width=85%>
			<bean:write name="sysSearchCateForm" property="fdParentName"/>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-search" key="sysSearchCate.fdName"/>
		</td><td width=85%>
			<bean:write name="sysSearchCateForm" property="fdName"/>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>