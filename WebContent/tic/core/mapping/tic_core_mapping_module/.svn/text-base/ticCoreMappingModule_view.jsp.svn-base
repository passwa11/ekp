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
	<kmss:auth requestURL="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticCoreMappingModule.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticCoreMappingModule.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-mapping" key="table.ticCoreMappingModule"/></p>

<center>
<table class="tb_normal" width=95%>
	<%-- 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdServerName"/>
		</td><td width="35%">
			<xform:text property="fdServerName" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdServerIp"/>
		</td><td width="35%">
			<xform:text property="fdServerIp" style="width:85%" />
		</td>
	</tr>
	--%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdModuleName"/>
		</td><td width="35%">
			<xform:text property="fdModuleName" style="width:85%" />
		</td>
     <td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdUse"/>
		</td><td width="35%">
			<xform:radio property="fdUse">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
	</tr>
	<tr>
			<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdTemplateName"/>
		</td><td width="35%">
			<xform:text property="fdTemplateName" style="width:85%" /><br>
			<xform:radio property="fdCate">
				<xform:enumsDataSource enumsType="ticCoreMappingModule_cate" />
			</xform:radio>
		</td>
			<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdMainModelName"/>
		</td><td width="35%">
			<xform:text property="fdMainModelName" style="width:85%" />
		</td>
	</tr>
	<tr style="display: ${ticCoreMappingModuleForm.fdCate==0?'none':''}">
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdTemCateFieldName"/>
		</td><td width="35%">
		<xform:text property="fdTemCateFieldName" value="" style="width:70%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdTemNameFieldName"/>
		</td><td width="35%">
		<xform:text property="fdTemNameFieldName" value="" style="width:70%" />
		</td>
	</tr>
		<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdModelTemFieldName"/>
		</td><td width="35%">
		<xform:text property="fdModelTemFieldName" value="" style="width:85%"/>
		</td>
			<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdFormTemFieldName"/>
		</td><td width="35%">
		<xform:text property="fdFormTemFieldName" value="" style="width:70%" />
		</td>
	</tr>
	<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdType"/>
		</td><td width="35%" id="td_type">
			 <xform:checkbox property="fdType" value="${ticCoreMappingModuleForm.fdType}" isArrayValue="true" showStatus="view">
		     <xform:customizeDataSource className="com.landray.kmss.tic.core.mapping.plugins.taglib.TicCorePluginsDataSource"/>
		     </xform:checkbox>
		</td>
		<td class="td_normal_title" width=15%>

		</td><td width="35%">

		
		</td>
	</tr>	
</table>
</center>
<script type="text/javascript">
window.onload = function(){
	   //alert(document.getElementById("td_type").children.length);
	   document.getElementById("td_type").children[2].style.display = "none";
}
</script>

<%@ include file="/resource/jsp/view_down.jsp"%>
