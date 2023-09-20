<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>
seajs.use("sys/tag/resource/css/tag_cloud.css");
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;

}
</script>
<c:import
	url="/sys/tag/sys_tag_category/sysTagCategory_addTags_button.jsp"
	charEncoding="UTF-8">
</c:import>
<c:if test="${param.fdId != '15a38f51c4442416006cbac4015835a1'}">
	<c:import
		url="/sys/tag/sys_tag_category/sysTagCategory_removeTags_button.jsp"
		charEncoding="UTF-8">
	</c:import>
</c:if>
<div id="optBarDiv">
	<c:if test="${param.fdId != '15a38f51c4442416006cbac4015835a1'}">
		<kmss:auth requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('sysTagCategory.do?method=edit&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.delete"/>"
				onclick="if(!confirmDelete())return;Com_OpenWindow('sysTagCategory.do?method=delete&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
	</c:if>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message  bundle="sys-tag" key="table.sysTagCategory"/></p>
<center>
<table class="tb_normal" width=95%>
		<html:hidden name="sysTagCategoryForm" property="fdId"/>
	<tr>

		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdName"/>
		</td><td width=35%>
			<c:out value="${sysTagCategoryForm.fdName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdOrder"/>
		</td><td width=35%>
			<c:out value="${sysTagCategoryForm.fdOrder}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdIsSpecial"/>
		</td><td width=35%>
			<xform:radio property="fdIsSpecial" showStatus="">
				<xform:enumsDataSource enumsType="sysTagIsSpecial_YesOrNo" />
			</xform:radio>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-tag" key="sysTagCategory.fdManagerId"/>
		</td><td width=35%>
			<c:out value="${sysTagCategoryForm.authEditorNames}" />
		</td>	
		
	</tr>
	<%---隐藏标签数---%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdTagQuoteTimes"/>
		</td><td colspan=3>
			${sysTagCategoryForm.fdTagQuoteTimes}
			<span class="txtstrong">(<bean:message  bundle="sys-tag" key="sysTagCategory.fdTagQuoteTimes.describe"/>)</span>
		</td>
	</tr>
	
	<%---标签云图---%>
	<tr>
		<td class="td_normal_title"><bean:message  bundle="sys-tag" key="sysTagCategory.alltags"/></td>
		<td colspan="3">		
		<ui:dataview>
			<ui:source type="AjaxJson">
				{"url":"/sys/tag/sys_tag_portlet/sysTagPortlet.do?method=getHotTags&dataInfoType=categoryCloud&categoryId=${param.fdId}"}
			</ui:source>
			<ui:render type="Javascript">
				<c:import url="/sys/tag/resource/js/tag_cloud.js" charEncoding="UTF-8"></c:import>
			</ui:render>
		</ui:dataview>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>