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
	<kmss:auth requestURL="/tic/core/provider/tic_core_node/ticCoreNode.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('ticCoreNode.do?method=edit&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/tic/core/provider/tic_core_node/ticCoreNode.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('ticCoreNode.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="tic-core-provider" key="table.ticCoreNode"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeLevel"/>
		</td><td width="35%">
			<xform:text property="fdNodeLevel" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeName"/>
		</td><td width="35%">
			<xform:text property="fdNodeName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodePath"/>
		</td><td width="35%">
			<xform:text property="fdNodePath" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdDataType"/>
		</td><td width="35%">
			<xform:text property="fdDataType" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeEnable"/>
		</td><td width="35%">
			<xform:radio property="fdNodeEnable">
				<xform:enumsDataSource enumsType="common_yesno" />
			</xform:radio>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdDefName"/>
		</td><td width="35%">
			<xform:text property="fdDefName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeContent"/>
		</td><td width="35%">
			<xform:text property="fdNodeContent" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-core-provider" key="ticCoreNode.fdIface"/>
		</td><td width="35%">
			<c:out value="${ticCoreNodeForm.fdIfaceName}" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>