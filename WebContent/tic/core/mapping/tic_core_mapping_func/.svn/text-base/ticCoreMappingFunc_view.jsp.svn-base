<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/tic/core/mapping/tic_core_mapping_func/ticCoreMappingFunc.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticCoreMappingFunc.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/core/mapping/tic_core_mapping_func/ticCoreMappingFunc.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticCoreMappingFunc.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-mapping" key="table.erp"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdTemplateId"/>
		</td><td width="35%">
			<xform:text property="fdTemplateId" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdInvokeType"/>
		</td><td width="35%">
			<xform:text property="fdInvokeType" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdOrder"/>
		</td><td width="35%">
			<xform:text property="fdOrder" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdFuncMark"/>
		</td><td width="35%">
			<xform:text property="fdFuncMark" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdRfcImport"/>
		</td><td width="35%">
			<xform:text property="fdRfcImport" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdRfcExport"/>
		</td><td width="35%">
			<xform:text property="fdRfcExport" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdJspSegmen"/>
		</td><td width="35%">
			<xform:text property="fdJspSegmen" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdQuartzId"/>
		</td><td width="35%">
			<xform:text property="fdQuartzId" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdUse"/>
		</td><td width="35%">
			<xform:radio property="fdUse">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdQuartzTime"/>
		</td><td width="35%">
			<xform:datetime property="fdQuartzTime" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdRfcSetting"/>
		</td><td width="35%">
			<c:out value="${ticCoreMappingFuncForm.fdRfcSettingName}" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.fdMain"/>
		</td><td width="35%">
			<c:out value="${ticCoreMappingFuncForm.fdMainName}" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
