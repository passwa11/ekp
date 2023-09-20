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
<kmss:auth
	requestURL="/sys/category/sys_category_property/sysCategoryProperty.do?method=edit&fdId=${param.fdId}"
	requestMethod="GET">
	<input type="button" value="<bean:message key="button.edit"/>"
		onclick="Com_OpenWindow('sysCategoryProperty.do?method=edit&fdId=${JsParam.fdId}','_self');">
</kmss:auth> <kmss:auth
	requestURL="/sys/category/sys_category_property/sysCategoryProperty.do?method=delete&fdId=${param.fdId}"
	requestMethod="GET">
	<input type="button" value="<bean:message key="button.delete"/>"
		onclick="if(!confirmDelete())return;Com_OpenWindow('sysCategoryProperty.do?method=delete&fdId=${JsParam.fdId}','_self');">
</kmss:auth> <input type="button" value="<bean:message key="button.close"/>"
	onclick="Com_CloseWindow();"></div>
<p class="txttitle"><bean:message bundle="sys-category"
	key="table.sysCategoryProperty" /></p>
<center>
<center>
	<table class="tb_normal" width=95%>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategoryProperty.fdParentName" /></td>
			<td colspan="3"><bean:write name="sysCategoryPropertyForm" property="fdParentName"/></td>
		</tr>		
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategoryProperty.fdName" /></td>
			<td colspan="3"><bean:write name="sysCategoryPropertyForm" property="fdName"/></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdOrder" /></td>
			<td colspan="3"><bean:write name="sysCategoryPropertyForm" property="fdOrder"/></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.tempEditorName" /></td>
			<td colspan="3"><bean:write name="sysCategoryPropertyForm" property="authEditorNames"/></td>
		</tr>
		<tr  style="display:none">
			<td class="td_normal_title" width=15%><bean:message
				key="model.tempReaderName" /></td>
			<td colspan="3"><bean:write name="sysCategoryPropertyForm" property="authReaderNames"/></td>
		</tr>
		<tr  style="display:none">
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategory.fdIsinheritMaintainer" /></td>
			<td width=35%>
			<sunbor:enumsShow value="${sysCategoryPropertyForm.fdIsinheritMaintainer}" enumsType="common_yesno" />
			</td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategory.fdIsinheritUser" /></td>
			<td width=35%>
			<sunbor:enumsShow value="${sysCategoryPropertyForm.fdIsinheritUser}" enumsType="common_yesno" />
			</td>			
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdCreator" /></td>
			<td width=35%><bean:write name="sysCategoryPropertyForm" property="docCreatorName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdCreateTime" /></td>
			<td width=35%><bean:write name="sysCategoryPropertyForm" property="docCreateTime"/></td>			
		</tr>
		<c:if test="${sysCategoryPropertyForm.docAlterorName!=''}">
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.docAlteror" /></td>
			<td width=35%><bean:write name="sysCategoryPropertyForm" property="docAlterorName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdAlterTime" /></td>
			<td width=35%><bean:write name="sysCategoryPropertyForm" property="docAlterTime"/></td>
		</tr>
		</c:if>
	</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
